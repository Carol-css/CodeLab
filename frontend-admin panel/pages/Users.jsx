import React, { useState, useEffect } from "react";
import Sidebar from "../components/Sidebar";
import axios from "axios";

const API_URL = "http://localhost:5000/api/users"; // Adjust based on your backend URL

const Users = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await axios.get(API_URL);
      console.log("Fetched Users:", response.data); // Debugging log
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };
  

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "20px" }}>
        <h2>Users List</h2>
        <table style={tableStyle}>
          <thead>
            <tr>
              <th style={tableHeaderStyle}>UID</th>
              <th style={tableHeaderStyle}>Display Name</th>
              <th style={tableHeaderStyle}>Email</th>
              <th style={tableHeaderStyle}>Profile Picture</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.uid}>
                <td style={tableCellStyle}>{user.uid}</td>
                <td style={tableCellStyle}>{user.displayName}</td>
                <td style={tableCellStyle}>{user.email}</td>
                <td style={tableCellStyle}>
                  {user.photoURL ? (
                    <img src={user.photoURL} alt="Profile" style={imageStyle} />
                  ) : (
                    "No Image"
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

// Styles
const tableStyle = { width: "100%", borderCollapse: "collapse", background: "white", borderRadius: "8px", overflow: "hidden" };
const tableHeaderStyle = { padding: "12px", textAlign: "center", background: "#007bff", color: "white" };
const tableCellStyle = { padding: "10px", borderBottom: "1px solid #ddd", textAlign: "center" };
const imageStyle = { width: "40px", height: "40px", borderRadius: "50%" };

export default Users;
