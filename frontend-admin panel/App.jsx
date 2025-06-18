import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AdminLogin from "./pages/AdminLogin"; // Import the Admin Login page
import Dashboard from "./pages/Dashboard"; // Import the Dashboard page
import "./App.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<AdminLogin />} /> {/* Admin Login as Home Page */}
        <Route path="/dashboard" element={<Dashboard />} /> {/* Admin Dashboard */}
      </Routes>
    </Router>
  );
}

export default App;
