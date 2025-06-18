package com.example.codelab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import dao.BookmarkDao;
import database.BookmarkDatabase;
import models.Bookmark;

public class JavaContentM1 extends AppCompatActivity {

    private FloatingActionButton btnBookmark;
    private boolean isBookmarked = false;
    private String moduleTitle, courseName = "Java"; // Example category
    private BookmarkDao bookmarkDao;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    public VideoPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_content_m1);

        // Get Data from Intent (Clicked Bookmark)
        moduleTitle = getIntent().getStringExtra("MODULE_TITLE"); // ✅ Fix: Store as Class Variable
        courseName = getIntent().getStringExtra("COURSE_NAME"); // ✅ Store Course Name

        TextView title = findViewById(R.id.videoTitle);
        title.setText(moduleTitle); // ✅ Display Lesson Title


        tabLayout = findViewById(R.id.tabLayout3);
        viewPager = findViewById(R.id.viewPager3);

        // ✅ Scroll to correct lesson when opening from a bookmark
        int lessonPosition = getLessonPosition(moduleTitle); // 🔹 Renamed variable to `lessonPosition`
        viewPager.post(() -> viewPager.setCurrentItem(lessonPosition, true)); // Smooth scroll to correct tab

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ImageButton btnBack = findViewById(R.id.homeBtn);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(JavaContentM1.this, Dashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish current activity to avoid stacking
        });



        btnBookmark = findViewById(R.id.btnBookmark);
        bookmarkDao = Room.databaseBuilder(this, BookmarkDatabase.class, "bookmark_database")
                .allowMainThreadQueries().build().bookmarkDao();

        checkIfBookmarked(); // Check bookmark status

        btnBookmark.setOnClickListener(v -> {
            if (isBookmarked) {
                removeBookmark();
            } else {
                addBookmark();
            }
        });

        // List of fragments with different videos and theory content
        List<VideoFragment> fragmentList = new ArrayList<>();
        fragmentList.add(VideoFragment.newInstance(R.raw.java_m1, "What is Java? Let’s Make it Fun!\n\nImagine Java is like a magic recipe book that helps you cook different dishes (apps and websites) that can be served anywhere in the world, no matter what kind of kitchen (device) you have!\n\n☕ Why is it Called Java?\n\nThe creators of Java loved coffee ☕, so they named it after their favorite drink! That’s why Java’s logo is a steaming cup of coffee! Cool, right?\n\nHow Does Java Work?\n\n1. Write Your Code  – Just like writing a recipe in English!\n" +
                "2. Translate It  – Java has a special translator (JVM - Java Virtual Machine) that makes sure your recipe works in any kitchen (Windows, Mac, Android, etc.).\n" +
                "3. Run It Anywhere! – You don’t need to rewrite your recipe for different stoves (devices); it just works!\n\nWhy is Java So Popular?\n\n✔ It’s Easy to Learn! – Even beginners can cook up some cool programs.\n" +
                "✔ It Works Everywhere! – Java apps run on phones, computers, ATMs, and even space stations! \n" +
                "✔ Super Safe! – Java has built-in security, so your programs don’t get \"burned\"  by hackers.\n" +
                "✔ It Cleans Up for You! – Java has a garbage collector that removes old, unnecessary data (like cleaning up your kitchen after cooking). \n\nWhere is Java Used?\n\nAndroid Apps – Most mobile apps are made using Java!\n" +
                "Games – Popular games like Minecraft use Java!\n" +
                "Banking Systems – Banks trust Java because it’s safe and reliable!\n" +
                "Websites – Sites like Amazon & LinkedIn use Java behind the scenes!\n\nWhy Should You Learn Java?\n\n•\tYou can create mobile apps, games, and websites!\n" +
                "•\tIt helps you get high-paying jobs in tech!\n" +
                "•\tOnce you learn Java, other languages (like Python & C++) become easier!\n\nFinal Thoughts\n\nJava is like the Swiss Army knife of programming—it’s versatile, secure, and runs almost everywhere. If you want to build mobile apps, games, or powerful web applications, learning Java is a great choice!"));

        fragmentList.add(VideoFragment.newInstance(R.raw.java_m2, "Features of Java – Explained in a Fun Way!\n\nImagine Java is like a superhero who makes coding easy, safe, and fun! Here’s why Java is awesome:\n\n1. Write Once, Run Anywhere!\n" +
                "* Java is like a magic passport ✈ – once you write your code, it can travel and work on any device (Windows, Mac, Android, etc.).\n" +
                "* No need to rewrite it for different platforms! (Unlike some languages that need visa approvals everywhere.\n\n2. Simple & Easy to Learn!" +
                "Java is like Lego blocks – everything fits together neatly.\n" +
                "No messy syntax like C++ (which feels like assembling an IKEA table without a manual).\n" +
                "Even beginners can start building cool stuff quickly!\n\n3. Object-Oriented Programming (OOP)\n" +
                "* Think of Java like Minecraft – everything is made up of blocks (objects)!\n" +
                "* OOP helps organize and reuse code, just like having different tools in your coding toolbox.\n\nConclusion: Java is Like Your Best Friend! \n" +
                "✔ It works anywhere without complaining.\n" +
                "✔ It cleans up after itself (unlike some messy languages).\n" +
                "✔ It protects your code like a superhero.\n" +
                "✔ It helps you build amazing things – from mobile apps to AI!\n" +
                "Now, are you ready to become a Java Ninja?  Let’s go! \n"));

        fragmentList.add(VideoFragment.newInstance(R.raw.java_m3, "Writing Your First \"Hello, World!\" in Java – A Fun Adventure!" +
                "Welcome, brave coder! Today, we embark on an epic quest – writing our first Java program!\n\n\nStep 1: Setting the Stage\n" +
                "Imagine Java as a magical spell book . To make it work, we need some special words – just like Harry Potter needs the right spells! ⚡\n" +
                "Here’s our magic spell (a.k.a. Java code):\n" +
                "CODE :- \n" +
                "\n" +
                "public class HelloWorld {  \n" +
                "    public static void main(String[] args) {  \n" +
                "        System.out.println(\"Hello, World!\");  \n" +
                "    }  \n" +
                "}\n" +
                "Now, let’s decode the magic!\n\n\nStep 2: Breaking the Spell into Pieces \n" +
                "\n" +
                "# public class HelloWorld {\n" +
                "This is like naming our magic spell (or a movie title ).\n" +
                "Every Java program lives inside a \"class\" – think of it as a house  where our code stays.\n" +
                "\tThe name must match the file name (or Java gets confused ).\n" +
                "\n" +
                "#  public static void main(String[] args) {\n" +
                "\tThis is the Grand Entrance – where Java starts reading your code.\n" +
                "\tIf Java were a robot , this would be its \"ON\" button!\n" +
                "\n" +
                "# System.out.println(\"Hello, World!\");\n" +
                "\tThis is our hero's first words! \n" +
                "\t\"Hello, World!\" gets displayed on the screen like a message in a bottle.\n" +
                "\tSystem.out.println is like Java's megaphone  – it SHOUTS things on the screen!\n\nStep 3: Running the Program \n" +
                "Okay, time to bring it to life!\n" +
                "1.Save the file as HelloWorld.java. \n" +
                "\tJava is picky, so make sure the file name matches the class name!\n" +
                "2. Compile the code! \n" +
                "\tOpen the terminal (or command prompt) and type: -> javac HelloWorld.java\n" +
                "\tThis turns your code into Java’s secret language \n" +
                "\tIf there are no errors, congrats!  Your spell is working!\n" +
                "3. Run the program!\n" +
                "Now, type:->  java HelloWorld\n" +
                "BOOM! The screen proudly says:\n" +
                "Output : -> Hello, World!\n" +
                "** YOU DID IT! You’ve just made your computer talk! \n "));

        // Set up adapter
        adapter = new VideoPagerAdapter(this, fragmentList);
        viewPager.setAdapter(adapter);

        // Attach TabLayout to ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText("Concept " + (position + 1))
        ).attach();

        FloatingActionButton fabDashboard = findViewById(R.id.fab_dashboard);

// Show/hide FAB based on the selected page
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pageposition) {
                super.onPageSelected(pageposition);
                if (pageposition == 2) { // Third page (index starts from 0)
                    fabDashboard.setVisibility(View.VISIBLE);
                } else {
                    fabDashboard.setVisibility(View.GONE);
                }
            }
        });

// Navigate to Dashboard when FAB is clicked
        fabDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(JavaContentM1.this, Dashboard.class);
            startActivity(intent);
        });

    }

    private int getLessonPosition(String lessonTitle) {
        if (lessonTitle == null) return 0; // Default to first lesson if null

        if (lessonTitle.contains("What is Java")) return 0;
        if (lessonTitle.contains("Features of Java")) return 1;
        if (lessonTitle.contains("Hello, World!")) return 2;

        return 0; // Default to first lesson if not found
    }





    private void checkIfBookmarked() {
        Executors.newSingleThreadExecutor().execute(() -> {
            Bookmark existingBookmark = bookmarkDao.getBookmarkByTitle(moduleTitle);
            runOnUiThread(() -> {
                isBookmarked = existingBookmark != null;
                updateBookmarkIcon();
            });
        });
    }

    private void addBookmark() {
        Bookmark bookmark = new Bookmark(moduleTitle, courseName, "Lesson Content", true, System.currentTimeMillis());
        Executors.newSingleThreadExecutor().execute(() -> {
            bookmarkDao.insertBookmark(bookmark);
            runOnUiThread(() -> {
                isBookmarked = true;
                updateBookmarkIcon();
                Toast.makeText(this, "Bookmarked!", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void removeBookmark() {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookmarkDao.deleteBookmarkByTitle(moduleTitle);
            runOnUiThread(() -> {
                isBookmarked = false;
                updateBookmarkIcon();
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void updateBookmarkIcon() {
        btnBookmark.setImageResource(isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_border);
    }

}


