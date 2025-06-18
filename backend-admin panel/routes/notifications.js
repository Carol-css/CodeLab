import express from "express";
const router = express.Router();

let notifications = [
  { id: 1, title: "New Course Added", message: "React Advanced is now available!", date: new Date(), status: "unread" },
  { id: 2, title: "System Update", message: "The admin panel has been upgraded.", date: new Date(), status: "read" }
];

// Get all notifications
router.get("/", (req, res) => {
  res.json(notifications);
});

// Mark as Read
router.put("/:id", (req, res) => {
  const id = parseInt(req.params.id);
  notifications = notifications.map((notif) =>
    notif.id === id ? { ...notif, status: "read" } : notif
  );
  res.json({ message: "Notification marked as read" });
});

// Delete notification
router.delete("/:id", (req, res) => {
  const id = parseInt(req.params.id);
  notifications = notifications.filter((notif) => notif.id !== id);
  res.json({ message: "Notification deleted" });
});

export default router;
