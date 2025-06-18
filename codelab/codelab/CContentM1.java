package com.example.codelab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;

public class CContentM1 extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    public CVideoPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_content_m1);

        TextView title = findViewById(R.id.videoTitle);
        String moduleTitle = getIntent().getStringExtra("MODULE_TITLE");

        title.setText(moduleTitle);

        tabLayout = findViewById(R.id.tabLayout3);
        viewPager = findViewById(R.id.viewPager3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // List of fragments with different videos and theory content
        List<CVideoFragment> fragmentList = new ArrayList<>();
        fragmentList.add(CVideoFragment.newInstance(R.raw.cintro, "What is C? \n\n" +
                "A Superpower for Your Computer!\n" +
                "Imagine C is like the blueprint for building robots, video games, and even operating systems! \n" +
                "It gives you full control over your computer, making it one of the most powerful programming languages ever!\n\n" +
                "Why is It Called C?\n\n" +
                "Before C, there was a language called B. When Dennis Ritchie improved it in the 1970s, he decided to name it C! \n" +
                "Simple, right? But don’t be fooled—C is super powerful!\n\n" +
                "How Does C Work? \uD83D\uDEE0\uFE0F\n\n" +
                "1\uFE0F⃣ Write Your Code – Just like writing a cooking recipe!\n" +
                "2\uFE0F⃣ Compile It – A special tool (C Compiler) translates it into a language your computer understands.\n" +
                "3\uFE0F⃣ Run It Anywhere – Whether it's Windows, Linux, or even microcontrollers, C runs everywhere!\n\n" +
                "Why is C So Popular? \uD83D\uDE80\n\n" +
                "✔ Super Fast! – C is faster than most languages, making it perfect for games and real-time applications.\n" +
                "✔ Works Everywhere! – From tiny microcontrollers to supercomputers, C is everywhere!\n" +
                "✔ Gives Full Control! – You can manage memory, optimize speed, and tweak performance like a pro!\n" +
                "✔ Foundation of Other Languages! – C inspired Java, Python, C++, and many more!\n" +
                "\n\n" +
                "Where is C Used? \uD83C\uDF0D\n\n" +
                "\uD83D\uDCBB Operating Systems – Windows, Linux, and even MacOS have C at their core!\n" +
                "\uD83C\uDFAE Game Development – Many classic and modern games use C for speed!\n" +
                "\uD83D\uDCE1 Embedded Systems – From microwave ovens to smartwatches, C powers everyday gadgets.\n" +
                "\uD83D\uDEF0\uFE0F Space Exploration – NASA and SpaceX use C in their spacecraft systems!\n" +
                "\n\n" +
                "Why Should You Learn C?\n\n" +
                "\uD83D\uDFE2 You become a real programmer! C teaches you how computers work under the hood.\n" +
                "\uD83D\uDFE2 It’s the gateway to other languages! If you learn C, Python, Java, and C++ become a piece of cake! \uD83C\uDF70\n" +
                "\uD83D\uDFE2 It’s in high demand! Many top tech companies want C programmers! \uD83D\uDCB0\n\n"));

        fragmentList.add(CVideoFragment.newInstance(R.raw.cfeatures, "1\uFE0F⃣ Fast & Furious ⚡\n\n" +
                "C is super fast compared to other programming languages\n." + " It’s like a sports car—it runs closer to the machine, making programs execute lightning-fast!\n\n" +
                "\uD83D\uDE80 Example: This is why games, operating systems, and real-time applications use C!\n" +
                "\n" +
                "2\uFE0F⃣ Portable – Code Once, Run Anywhere! \uD83C\uDF0D\n\n" +
                "Write C code on one machine, and with little to no changes, it runs on another! It’s like a USB drive—you can plug it into different computers and it still works.\n\n" +
                "\uD83E\uDDF3 Example: A C program written on Windows can easily run on Linux or Mac with small modifications!\n" +
                "\n" +
                "3\uFE0F⃣ Power of Pointers – Your Superpower! \uD83E\uDDB8\u200D♀\uFE0F\n\n" +
                "Pointers in C let you control memory directly, like Doctor Strange’s magic portals! " +
                "With pointers, you can:\n" +
                "✅ Optimize memory usage\n" +
                "✅ Access and modify data super efficiently\n" +
                "✅ Speed up your programs\n" +
                "\n" +
                "\uD83E\uDE84 Example: Many advanced applications, like game engines and databases, use pointers for performance!\n" +
                "\n" +
                "4\uFE0F⃣ Memory Management – The Boss of RAM! \uD83C\uDFD7\uFE0F\n\n" +
                "C lets you allocate and deallocate memory manually, which is like renting and returning an apartment. " +
                "You decide how much space to use!\n\n" +
                "\uD83D\uDCBE Example:\n" +
                "With functions like malloc() and free(), you can dynamically manage memory while running your program!\n" +
                "\n" +
                "5\uFE0F⃣ Simple & Elegant – No Extra Baggage! \uD83C\uDFA9\n\n" +
                "C doesn’t have unnecessary features like Java’s “heavy” classes or Python’s “magic” syntax. " +
                "It’s a lightweight, no-nonsense language—simple but powerful!\n" +
                "\n" +
                "6\uFE0F⃣ Modularity – Build Your Code Like LEGO! \uD83E\uDDF1\n\n" +
                "C allows you to break your program into functions—just like LEGO blocks, making it organized, reusable, and easy to debug!\n" +
                "\n" +
                "7\uFE0F⃣ Rich Library Support – C Has a Toolbox! \uD83D\uDD27\n\n" +
                "C comes with a powerful standard library full of pre-written functions. " +
                "It’s like having a toolbox that helps you:\n" +
                "✅ Perform math calculations (math.h)\n" +
                "✅ Handle input and output (stdio.h)\n" +
                "✅ Work with strings (string.h)\n" +
                "\n" +
                "8\uFE0F⃣ Structured Language – Code with a Game Plan! \uD83C\uDFAE\n\n" +
                "C follows a step-by-step, structured approach, just like following a recipe. " +
                "It helps programmers write clear, logical code that’s easy to maintain.\n" +
                "\n" +
                "9\uFE0F⃣ Extensible – The Language That Keeps Growing! \uD83D\uDE80\n\n" +
                "C is like LEGO Technic—you can extend it with libraries and modules, making it more powerful! " +
                "Many modern languages like C++, Java, and Python are based on C!\n" +
                "\n" +
                "\uD83D\uDD17 Example: If you know C, learning C++, Java, or even Python becomes way easier!\n\n"));

        fragmentList.add(CVideoFragment.newInstance(R.raw.firstc, "Writing your first “Hello, World!” program\n" +
                "\n" +
                "Welcome, future coding wizard! \n" +
                "\uD83E\uDDD9\u200D♂\uFE0F Today, we’re going on a magical journey to write and run your very first C program—the legendary “Hello, World!”\n" +
                "\n" +
                "Step 1: Setting the Stage:\n\n" +
                "Imagine C as a wizard’s spell book—full of powerful commands to make computers do your bidding! " +
                "But just like in Harry Potter, you need the right words (code) for the magic to work! ⚡\n\n" +
                "Here’s our magic spell (a.k.a. C code):-\n" +
                "#include <stdio.h>  \n" +
                "Int main() {  \n" +
                "    Printf(“Hello, World!\\n”);  \n" +
                "    Return 0;  \n" +
                "}\n\n" +
                "Now, let’s decode the magic and see how it works! \uD83E\uDDD0\uD83D\uDD0D\n" +
                "\n" + "Step 2: Breaking the Spell into Pieces:\n\n" +
                "\uD83D\uDD39 #include <stdio.h> – The Spellbook \uD83D\uDCDC\n\n" +
                "This is like calling upon an ancient book of spells! \uD83D\uDCD6✨\n" + "\n" +
                "\uD83D\uDD39" + "#include -->" + "tells the compiler to borrow functions from another file.\n\n" +
                "\uF0B7" + "<stdio.h> -->" + "(Standard Input/Output library) contains the printf() function, which we need to display text.\n" +
                "\uF0B7" + "Without this, our spell won’t work!\n" +
                "\n" +
                "\uD83D\uDD39 " + "int main() { – The Grand Entrance \uD83D\uDEAA\n\n" +
                "\uF0B7" + "Every C program starts from main(), just like every adventure starts with a hero’s first step! \uD83C\uDFF0\n" +
                "\uF0B7" + "Think of main() as the front door to your program’s house! \uD83D\uDEAA\n" +
                "\uF0B7" + "When you run the program, this is where the computer enters and starts executing code!\n" +
                "\n" +
                "\uD83D\uDD39 printf(“Hello, World!\\n”); – The Hero’s First Words! \uD83D\uDDE3\uFE0F\n\n" +
                "\uF0B7" + "Printf() is like C’s magic megaphone, letting your program say something on the screen!\n" +
                "\uF0B7" +
                "“Hello, World!” is what we want to display (change it to your name if you like!).\n" +
                "\uF0B7\\n moves the cursor to a new line (like pressing Enter on your keyboard).\n\n" +
                "\uD83D\uDD39 return 0; – The Happy Ending \uD83C\uDFAC\n\n" +
                "Every great spell needs a proper ending! \uD83C\uDFC1\n" +
                "\uF0B7Return 0; tells the computer, “Mission Accomplished! No errors found!” ✅\n" +
                "\uF0B7It exits the program smoothly, ensuring everything worked fine.\n\n" +
                "Step 3: Running the Program – Bringing Your Spell to Life! \uD83E\uDDD9\u200D♂\uFE0F⚡\n\n" +
                "1\uFE0F⃣ Save Your Spell (C Code) \uD83D\uDCDC\n\n" +
                "Before running the program, we need to save it properly.\n" +
                "\uF0B7Open any text editor (VS Code, CodeBlocks, Notepad++, etc.).\n\n" +
                "\uF0B7Type the following C code:\n" +
                "#include <stdio.h>\n" +
                "Int main() {\n" +
                "    Printf(“Hello, World!\\n”);\n" +
                "    Return 0;\n" +
                "}\n\n" +
                "\uF0B7Save the file as hello.c\n\n" +
                "(\uD83D\uDD38 Make sure it ends with .c, or the computer won’t understand it! ❌) \n\n" +
                "2\uFE0F⃣ Open the Terminal – The Wizard’s Command Center! \uD83D\uDCBB\n" +
                "To run your spell, you need to speak the language of the machine—this is done through the terminal or command prompt.\n\n" +
                "\uF0B7On Windows: Open Command Prompt (cmd)\n" +
                "\uF0B7On macOS/Linux: Open Terminal\n\n" +
                "3\uFE0F⃣ Compile the Code – Turning Spells into Machine Language! \uD83C\uDFD7\uFE0F\n\n" +
                "Computers don’t understand human-written spells (C code) directly—they need it translated! \n" +
                "This is where a compiler (like gcc) comes in.\n" +
                "In the terminal, navigate to where your hello.c file is saved. " +
                "Then type:- Gcc hello.c -o hello\n" +
                "\n" +
                "\uD83D\uDD39 What this does:\n" +
                "Gcc → Calls the GNU Compiler Collection (a C translator \uD83D\uDEE0\uFE0F).\n" +
                "Hello.c → The source file we wrote.\n" +
                "-o hello → Creates an executable file named hello (or hello.exe on Windows).\n\n" +
                "\uD83D\uDD38 If there are no errors, the compilation is successful! \uD83C\uDF89\n\n" +
                "4\uFE0F⃣ Run Your Program – Let the Magic Happen! ✨\n" +
                "Now, it’s time to see the results! Run the compiled program:\n\n" +
                "\uD83D\uDD39 On Windows: Hello.exe\n\n" +
                "\uD83C\uDF89 Boom! You should see this on the screen:\n" +
                "Hello, World!\n\n" +
                "Congratulations! You just wrote, compiled, and executed your first C program! \uD83C\uDF8A\uD83D\uDD25\n\n" +
                "Final Thoughts – You Did It! \uD83D\uDE80\n\n" +
                "You’ve now written, compiled, and run your first C program! \uD83C\uDF89 \n" +
                "This is just the beginning of your journey into the powerful world of C programming!\n" +
                "\n" +
                "Next steps:\n" +
                "✅ Try printing your name instead of “Hello, World!”\n" +
                "✅ Experiment with multiple printf() statements\n" +
                "✅ Move to variables and user input!\n" +
                "\n" +
                "Keep coding, and let the adventure continue! \uD83D\uDD25\uD83D\uDCBB\n" +
                "\n\n"));

        // Set up adapter
        adapter = new CVideoPagerAdapter(this, fragmentList);
        viewPager.setAdapter(adapter);

        // Attach TabLayout to ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText("Concept " + (position + 1))
        ).attach();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton fabDashboard = findViewById(R.id.fab_dashboard);

// Show/hide FAB based on the selected page
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) { // Third page (index starts from 0)
                    fabDashboard.setVisibility(View.VISIBLE);
                } else {
                    fabDashboard.setVisibility(View.GONE);
                }
            }
        });

// Navigate to Dashboard when FAB is clicked
        fabDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(CContentM1.this, Dashboard.class);
            startActivity(intent);
        });

    }

}


