import express from "express";
const router = express.Router();

// Sample analytics data
router.get("/", (req, res) => {
  res.json({
    totalUsers: 1500,
    activeUsers: 320,
    totalCourses: 50,
    popularCourses: [
      { title: "React for Beginners", enrollments: 800 },
      { title: "Node.js Masterclass", enrollments: 600 },
      { title: "Python Fundamentals", enrollments: 700 },
    ],
    recentSignups: [
      { date: "2025-03-20", count: 25 },
      { date: "2025-03-21", count: 30 },
      { date: "2025-03-22", count: 40 },
    ],
  });
});

export default router;
