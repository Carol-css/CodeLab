import React from "react";
import Sidebar from "../components/Sidebar"; 
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";
import "../components/Dashboard.css";

const Dashboard = () => {
  const analyticsData = [
    { month: "Jan", users: 100, courses: 50 },
    { month: "Feb", users: 200, courses: 80 },
    { month: "Mar", users: 300, courses: 100 },
    { month: "Apr", users: 400, courses: 130 },
    { month: "May", users: 500, courses: 160 },
  ];

  return (
    <div className="dashboard-container">
      <Sidebar />

      <div className="dashboard-content">
        <h1>Admin Dashboard</h1>

        {/* Admin Statistics */}
        <div className="stats-container">
          <div className="stat-card users">
            <h2>Users</h2>
            <p>5,000+</p>
          </div>
          <div className="stat-card courses">
            <h2>Courses</h2>
            <p>120</p>
          </div>
          <div className="stat-card enrollments">
            <h2>Enrollments</h2>
            <p>20,000+</p>
          </div>
        </div>

        {/* Analytics Chart */}
        <div className="chart-container">
          <h2>User & Course Growth</h2>
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={analyticsData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Line type="monotone" dataKey="users" stroke="#8884d8" strokeWidth={2} />
              <Line type="monotone" dataKey="courses" stroke="#82ca9d" strokeWidth={2} />
            </LineChart>
          </ResponsiveContainer>
        </div>

        {/* Recent Activity */}
        <div className="recent-activity">
          <h2>Recent Activity</h2>
          <ul>
            <li>User <b>John Doe</b> enrolled in <b>React Basics</b></li>
            <li>New course <b>Advanced JavaScript</b> was added</li>
            <li>User <b>Alice Smith</b> signed up</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
