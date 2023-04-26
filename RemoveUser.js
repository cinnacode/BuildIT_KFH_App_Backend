// Install these dependencies
// npm install express mongoose

// Import the required modules and initialize the express app
const express = require('express');
const router = express.Router();
const User = require('../models/user');
const mongoose = require('mongoose');

// Connect to MongoDB database
mongoose
  .connect('mongodb://localhost:27017', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false,
  })
  .then(() => console.log('Connected to MongoDB'))
  .catch((error) => console.error('Failed to connect to MongoDB', error));

// Render the remove user page with a dropdown menu containing all users
router.get('/remove-user', async (req, res) => {
  try {
    const users = await User.find();
    res.render('removeUser', { users });
  } catch (error) {
    console.log(error);
    res.status(500).send('Internal server error');
  }
});

// Handle the user removal request
router.post('/remove-user', async (req, res) => {
  try {
    // Get the user id from the request body
    const { userId } = req.body;

    // Validate the user id
    if (!mongoose.Types.ObjectId.isValid(userId)) {
      return res.status(400).send('Invalid user id');
    }

    // Check if the user exists
    const user = await User.findById(userId);
    if (!user) {
      return res.status(404).send('User not found');
    }

    // Remove the user from the database
    await User.findByIdAndRemove(userId);

    res.redirect('/remove-user');
  } catch (error) {
    console.log(error);
    res.status(500).send('Internal server error');
  }
});

// Export the router
module.exports = router;
