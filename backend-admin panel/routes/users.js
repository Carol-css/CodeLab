import express from "express";
import admin from "../config/firebaseAdmin.js";
 // Import Firebase Admin SDK

const router = express.Router();

// Get list of users
router.get("/", async (req, res) => {
  try {
    const listUsers = await admin.auth().listUsers(1000); // Fetch up to 1000 users
    res.json(listUsers.users.map(user => ({
      uid: user.uid,
      email: user.email,
      displayName: user.displayName || "No Name",
      photoURL: user.photoURL || "",
    })));
  } catch (error) {
    console.error("Error fetching users:", error);
    res.status(500).json({ error: "Failed to fetch users" });
  }
});

export default router;
