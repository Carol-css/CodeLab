import React, { useState, useEffect } from "react";
import Sidebar from "../components/Sidebar";
import axios from "axios";

const API_URL = "http://localhost:5000/api/courses"; // Backend API URL

const Courses = () => {
  const [courses, setCourses] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [currentCourse, setCurrentCourse] = useState({
    id: null,
    title: "",
    instructor: "",
    description: "",
  });

  // Fetch courses from backend
  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const response = await axios.get(API_URL);
      setCourses(response.data);
    } catch (error) {
      console.error("Error fetching courses:", error);
    }
  };

  const handleAddCourse = () => {
    setCurrentCourse({ id: null, title: "", instructor: "", description: "" });
    setModalOpen(true);
  };

  const handleEditCourse = (course) => {
    setCurrentCourse(course);
    setModalOpen(true);
  };

  const handleDeleteCourse = async (id) => {
    if (window.confirm("Are you sure you want to delete this course?")) {
      try {
        await axios.delete(`${API_URL}/${id}`);
        fetchCourses();
      } catch (error) {
        console.error("Error deleting course:", error);
      }
    }
  };

  const handleSaveCourse = async (e) => {
    e.preventDefault();
    try {
      const formattedCourse = {
        title: currentCourse.title.trim(),
        instructor: currentCourse.instructor.trim(),
        description: currentCourse.description.trim(),
      };

      if (currentCourse.id) {
        // Edit existing course
        await axios.put(`${API_URL}/${currentCourse.id}`, formattedCourse);
      } else {
        // Add new course
        await axios.post(API_URL, formattedCourse);
      }

      setModalOpen(false);
      fetchCourses();
    } catch (error) {
      console.error("Error saving course:", error);
    }
  };

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "20px" }}>
        <h2>Manage Courses</h2>
        <button onClick={handleAddCourse} style={addButtonStyle}>+ Add New Course</button>

        <table style={tableStyle}>
          <thead>
            <tr>
              <th style={tableHeaderStyle}>ID</th>
              <th style={tableHeaderStyle}>Course Title</th>
              <th style={tableHeaderStyle}>Instructor</th>
              <th style={tableHeaderStyle}>Description</th>
              <th style={tableHeaderStyle}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {courses.map((course) => (
              <tr key={course.id}>
                <td style={tableCellStyle}>{course.id}</td>
                <td style={tableCellStyle}>{course.title}</td>
                <td style={tableCellStyle}>{course.instructor}</td>
                <td style={tableCellStyle}>{course.description}</td>
                <td style={tableCellStyle}>
                  <button onClick={() => handleEditCourse(course)} style={editButtonStyle}>Edit</button>
                  <button onClick={() => handleDeleteCourse(course.id)} style={deleteButtonStyle}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {modalOpen && (
          <div style={modalStyle}>
            <form onSubmit={handleSaveCourse} style={modalContentStyle}>
              <h3>{currentCourse.id ? "Edit Course" : "Add Course"}</h3>
              <input
                type="text"
                placeholder="Course Title"
                value={currentCourse.title}
                onChange={(e) => setCurrentCourse({ ...currentCourse, title: e.target.value })}
                required
              />
              <input
                type="text"
                placeholder="Instructor"
                value={currentCourse.instructor}
                onChange={(e) => setCurrentCourse({ ...currentCourse, instructor: e.target.value })}
                required
              />
              <input
                type="text"
                placeholder="Description"
                value={currentCourse.description}
                onChange={(e) => setCurrentCourse({ ...currentCourse, description: e.target.value })}
                required
              />
              <button type="submit" style={saveButtonStyle}>Save</button>
              <button onClick={() => setModalOpen(false)} style={cancelButtonStyle}>Cancel</button>
            </form>
          </div>
        )}
      </div>
    </div>
  );
};

// 🔹 Styles (Updated for Better UX/UI)
const addButtonStyle = { padding: "10px", background: "#007bff", color: "white", border: "none", borderRadius: "5px", cursor: "pointer", marginBottom: "20px" };
const tableStyle = { width: "100%", borderCollapse: "collapse", background: "white", borderRadius: "8px", overflow: "hidden" };
const tableHeaderStyle = { padding: "12px", textAlign: "center", background: "#007bff", color: "white" };
const tableCellStyle = { padding: "10px", borderBottom: "1px solid #ddd", textAlign: "center" };
const editButtonStyle = { padding: "5px 10px", marginRight: "5px", background: "#28a745", color: "white", border: "none", borderRadius: "5px", cursor: "pointer" };
const deleteButtonStyle = { padding: "5px 10px", background: "#dc3545", color: "white", border: "none", borderRadius: "5px", cursor: "pointer" };
const saveButtonStyle = { padding: "10px", background: "#28a745", color: "white", border: "none", borderRadius: "5px", cursor: "pointer", width: "100%" };
const cancelButtonStyle = { padding: "10px", background: "#6c757d", color: "white", border: "none", borderRadius: "5px", cursor: "pointer", width: "100%" };
const modalStyle = { position: "fixed", top: "0", left: "0", width: "100%", height: "100%", background: "rgba(0, 0, 0, 0.5)", display: "flex", justifyContent: "center", alignItems: "center" };
const modalContentStyle = { background: "white", padding: "20px", borderRadius: "10px", width: "300px", textAlign: "center", display: "flex", flexDirection: "column", gap: "10px" };

export default Courses;
