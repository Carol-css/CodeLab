import express from "express";
import pool from "../db.js"; // Import database connection

const router = express.Router();

// Fetch all courses
router.get("/", async (req, res) => {
  try {
    const result = await pool.query("SELECT * FROM public.courses ORDER BY id ASC");
    res.json(result.rows);
  } catch (err) {
    console.error("Error fetching courses:", err);
    res.status(500).json({ error: "Failed to fetch courses" });
  }
});

// Add a new course
router.post("/", async (req, res) => {
  try {
    const { title, description, instructor } = req.body;

    if (!title || !description || !instructor) {
      return res.status(400).json({ error: "All fields are required" });
    }

    const newCourse = await pool.query(
      "INSERT INTO public.courses (title, description, instructor) VALUES ($1, $2, $3) RETURNING *",
      [title, description, instructor]
    );

    res.status(201).json(newCourse.rows[0]); // Return the newly added course
  } catch (err) {
    console.error("Error adding course:", err);
    res.status(500).json({ error: "Failed to add course" });
  }
});

// Edit a course
router.put("/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const { title, description, instructor } = req.body;

    if (!title || !description || !instructor) {
      return res.status(400).json({ error: "All fields are required" });
    }

    const updatedCourse = await pool.query(
      "UPDATE public.courses SET title=$1, description=$2, instructor=$3 WHERE id=$4 RETURNING *",
      [title, description, instructor, id]
    );

    if (updatedCourse.rowCount === 0) {
      return res.status(404).json({ error: "Course not found" });
    }

    res.json(updatedCourse.rows[0]); // Return the updated course
  } catch (err) {
    console.error("Error updating course:", err);
    res.status(500).json({ error: "Failed to update course" });
  }
});

// Delete a course
router.delete("/:id", async (req, res) => {
  try {
    const { id } = req.params;

    const deletedCourse = await pool.query("DELETE FROM public.courses WHERE id=$1 RETURNING *", [id]);

    if (deletedCourse.rowCount === 0) {
      return res.status(404).json({ error: "Course not found" });
    }

    res.json({ message: "Course deleted successfully", deletedCourse: deletedCourse.rows[0] });
  } catch (err) {
    console.error("Error deleting course:", err);
    res.status(500).json({ error: "Failed to delete course" });
  }
});

export default router;
