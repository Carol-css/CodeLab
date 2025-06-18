import React, { useState, useEffect } from "react";
import Sidebar from "../components/Sidebar";
import axios from "axios";

const API_URL = "http://localhost:5000/api/notifications"; 

const Notifications = () => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    try {
      const response = await axios.get(API_URL);
      setNotifications(response.data);
    } catch (error) {
      console.error("Error fetching notifications:", error);
    }
  };

  const markAsRead = async (id) => {
    try {
      await axios.put(`${API_URL}/${id}`, { status: "read" });
      setNotifications((prev) =>
        prev.map((notif) =>
          notif.id === id ? { ...notif, status: "read" } : notif
        )
      );
    } catch (error) {
      console.error("Error updating notification:", error);
    }
  };

  const deleteNotification = async (id) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
      setNotifications((prev) => prev.filter((notif) => notif.id !== id));
    } catch (error) {
      console.error("Error deleting notification:", error);
    }
  };

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "20px" }}>
        <h2>Notifications</h2>
        {notifications.length === 0 ? (
          <p>No notifications available</p>
        ) : (
          <table style={tableStyle}>
            <thead>
              <tr>
                <th style={tableHeaderStyle}>Title</th>
                <th style={tableHeaderStyle}>Message</th>
                <th style={tableHeaderStyle}>Date</th>
                <th style={tableHeaderStyle}>Status</th>
                <th style={tableHeaderStyle}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {notifications.map((notif) => (
                <tr key={notif.id}>
                  <td style={tableCellStyle}>{notif.title}</td>
                  <td style={tableCellStyle}>{notif.message}</td>
                  <td style={tableCellStyle}>{new Date(notif.date).toLocaleString()}</td>
                  <td style={tableCellStyle}>
                    <span style={{ color: notif.status === "read" ? "green" : "red" }}>
                      {notif.status}
                    </span>
                  </td>
                  <td style={tableCellStyle}>
                    {notif.status !== "read" && (
                      <button onClick={() => markAsRead(notif.id)} style={buttonStyle}>
                        Mark as Read
                      </button>
                    )}
                    <button onClick={() => deleteNotification(notif.id)} style={deleteButtonStyle}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

// Styles
const tableStyle = { width: "100%", borderCollapse: "collapse", background: "white", borderRadius: "8px", overflow: "hidden" };
const tableHeaderStyle = { padding: "12px", textAlign: "center", background: "#007bff", color: "white" };
const tableCellStyle = { padding: "10px", borderBottom: "1px solid #ddd", textAlign: "center" };
const buttonStyle = { background: "#28a745", color: "white", border: "none", padding: "6px 12px", cursor: "pointer", marginRight: "5px" };
const deleteButtonStyle = { background: "#dc3545", color: "white", border: "none", padding: "6px 12px", cursor: "pointer" };

export default Notifications;
