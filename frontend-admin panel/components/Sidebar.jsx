import "../components/Sidebar.css";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { signOut } from "firebase/auth";
import { auth } from "../firebaseConfig";
import { FiMenu, FiLogOut } from "react-icons/fi"; // Import icons

const Sidebar = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await signOut(auth);
      navigate("/login"); // Redirect to login page after logout
    } catch (error) {
      console.error("Logout failed", error);
    }
  };

  return (
    <div className={`sidebar ${collapsed ? "collapsed" : ""}`}>
      <button className="menu-toggle" onClick={() => setCollapsed(!collapsed)}>
        <FiMenu />
      </button>

      <h2 className="sidebar-title">{collapsed ? "AP" : "Admin Panel"}</h2>

      <nav>
        <ul>
          <li><Link to="/">Dashboard</Link></li>
          <li><Link to="/courses">Manage Courses</Link></li>
          <li><Link to="/users">User Management</Link></li>
          <li><Link to="/notifications">Notifications</Link></li>
          <li><Link to="/analytics">Analytics</Link></li>
        </ul>
      </nav>

      <button className="logout-btn" onClick={handleLogout}>
        <FiLogOut /> {collapsed ? "" : "Logout"}
      </button>
    </div>
  );
};

export default Sidebar;
