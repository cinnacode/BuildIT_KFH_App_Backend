// Install these dependencies
// npm install express body-parser mongoose

// Import necessary modules
const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

// Create Express app
const app = express();

// Connect to MongoDB
mongoose.connect('mongodb://localhost:27017', { useNewUrlParser: true, useUnifiedTopology: true });
const db = mongoose.connection;

// Define User schema
const userSchema = new mongoose.Schema({
  email: { type: String, required: true, unique: true },
  role: { type: String, required: true },
  team: String,
  business: String
});

// Create User model
const User = mongoose.model('User', userSchema);

// Middleware for parsing request body
app.use(bodyParser.urlencoded({ extended: true }));

// Route for handling form submission
app.post('/adduser', (req, res) => {
  const { email, role, team, business } = req.body;

  // Validate request body
  if (!email || !role) {
    return res.status(400).json({ error: 'Email and Role are required' });
  }

  // Check if email is valid
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    return res.status(400).json({ error: 'Invalid email format' });
  }

  // Check if email already exists in database
  User.findOne({ email })
    .then(existingUser => {
      if (existingUser) {
        return res.status(400).json({ error: 'Email already exists' });
      }

      // Create new User document
      const newUser = new User({ email, role });

      // Set team or business based on role
      if (role === 'tester') {
        if (!team) {
          return res.status(400).json({ error: 'Team is required for Tester role' });
        }
        newUser.team = team;
      } else if (role === 'business') {
        if (!business) {
          return res.status(400).json({ error: 'Business is required for Business role' });
        }
        newUser.business = business;
      }

      // Save User document to MongoDB
      newUser.save()
        .then(user => {
          res.status(200).json({ success: true, data: user });
        })
        .catch(err => {
          res.status(500).json({ error: 'Failed to add user' });
        });
    })
    .catch(err => {
      res.status(500).json({ error: 'Failed to check email existence' });
    });
});

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err);
  res.status(500).json({ error: 'Internal Server Error' });
});

// Start server
app.listen(3000, () => {
  console.log('Server started on http://localhost:3000');
});

