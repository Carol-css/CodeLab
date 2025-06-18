import React, { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import axios from "axios";
import { Bar, Line, Pie } from "react-chartjs-2";
import "chart.js/auto"; // Ensure Chart.js is installed

const API_URL = "http://localhost:5000/api/analytics"; // Adjust based on your backend

const Analytics = () => {
  const [analytics, setAnalytics] = useState({
    totalUsers: 0,
    activeUsers: 0,
    totalCourses: 0,
    popularCourses: [],
    recentSignups: [],
  });

  useEffect(() => {
    fetchAnalytics();
  }, []);

  const fetchAnalytics = async () => {
    try {
      const response = await axios.get(API_URL);
      setAnalytics(response.data);
    } catch (error) {
      console.error("Error fetching analytics:", error);
    }
  };

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "20px" }}>
        <h2>📊 Analytics Dashboard</h2>

        {/* Overview Cards */}
        <div style={{ display: "flex", gap: "20px", marginBottom: "20px" }}>
          <Card title="Total Users" value={analytics.totalUsers} />
          <Card title="Active Users" value={analytics.activeUsers} />
          <Card title="Total Courses" value={analytics.totalCourses} />
        </div>

        {/* Charts Section */}
        <div style={{ display: "flex", gap: "20px", marginBottom: "20px" }}>
          {/* Popular Courses */}
          <div style={chartContainer}>
            <h3>📈 Most Popular Courses</h3>
            <Bar
              data={{
                labels: analytics.popularCourses.map((c) => c.title),
                datasets: [
                  {
                    label: "Enrollments",
                    data: analytics.popularCourses.map((c) => c.enrollments),
                    backgroundColor: "rgba(54, 162, 235, 0.6)",
                  },
                ],
              }}
            />
          </div>

          {/* Recent Signups */}
          <div style={chartContainer}>
            <h3>🆕 Recent Signups</h3>
            <Line
              data={{
                labels: analytics.recentSignups.map((s) => s.date),
                datasets: [
                  {
                    label: "New Users",
                    data: analytics.recentSignups.map((s) => s.count),
                    backgroundColor: "rgba(75, 192, 192, 0.6)",
                  },
                ],
              }}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

// Card Component for Summary Stats
const Card = ({ title, value }) => (
  <div style={cardStyle}>
    <h3>{title}</h3>
    <p>{value}</p>
  </div>
);

// Styles
const cardStyle = {
  padding: "20px",
  background: "#007bff",
  color: "white",
  borderRadius: "8px",
  flex: 1,
  textAlign: "center",
};

const chartContainer = {
  flex: 1,
  background: "white",
  padding: "20px",
  borderRadius: "8px",
};

export default Analytics;
