// Install these dependencies
// npm install express body-parser express-validator mongodb bcrypt


const MongoClient = require('mongodb').MongoClient;
const express = require('express');
const app = express();
const helmet = require('helmet');
const { body, validationResult } = require('express-validator');

// Configure middleware for security
app.use(helmet());

// Connect to the MongoDB database
const url = 'mongodb://localhost:27017';
const dbName = 'mydb';
const client = new MongoClient(url, { useNewUrlParser: true });
let db;

client.connect((err) => {
  if (err) {
    console.error(err);
    return;
  }
  console.log('Connected to MongoDB database');

  db = client.db(dbName);
});

// Route for the edit user page
app.get('/edit-user', (req, res) => {
  // Get all the users from the database
  db.collection('users').find({}).toArray((err, users) => {
    if (err) {
      console.error(err);
      return res.status(500).send('Error getting users from database');
    }

    // Render the edit user page with the list of users
    res.render('edit-user', { users });
  });
});

// Route for submitting the edit user form
app.post(
  '/edit-user',
  [
    body('userId').notEmpty().withMessage('User ID is required'),
    body('role')
      .notEmpty()
      .withMessage('Role is required')
      .isIn(['developer', 'tester', 'business'])
      .withMessage('Invalid role'),
    body('teamName')
      .if((value, { req }) => req.body.role === 'tester')
      .notEmpty()
      .withMessage('Team name is required'),
    body('businessName')
      .if((value, { req }) => req.body.role === 'business')
      .notEmpty()
      .withMessage('Business name is required'),
  ],
  (req, res) => {
    // Validate the form data
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }

    const { userId, role, teamName, businessName } = req.body;

    // Check if the user ID exists in the database
    db.collection('users').findOne({ _id: ObjectId(userId) }, (err, user) => {
      if (err) {
        console.error(err);
        return res.status(500).send('Error finding user in database');
      }

      if (!user) {
        return res.status(400).send('User not found in database');
      }

      // Update the user's role and team/business name in the database
      db.collection('users').updateOne(
        { _id: ObjectId(userId) },
        {
          $set: {
            role,
            ...(role === 'tester' && { teamName }),
            ...(role === 'business' && { businessName }),
          },
        },
        (err) => {
          if (err) {
            console.error(err);
            return res.status(500).send('Error updating user in database');
          }

          // Redirect back to the edit user page
          res.redirect('/edit-user');
        }
      );
    });
  }
);

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).send('Internal server error');
});

// Start the server
app.listen(3000, () => {
  console.log('Server started on port 3000');
});
