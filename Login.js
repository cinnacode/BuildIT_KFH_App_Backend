// Install these dependencies
// npm install express mongodb body-parser cors bcrypt

// Import necessary packages
const express = require('express');
const MongoClient = require('mongodb').MongoClient;
const bodyParser = require('body-parser');
const cors = require('cors');
const bcrypt = require('bcrypt');

// Create Express app
const app = express();

// Use middleware
app.use(bodyParser.json());
app.use(cors());

// Connect to MongoDB database
const uri = 'mongodb://localhost:27017';
const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true });

client.connect(err => {
    if (err) {
        console.error(err);
        process.exit(1);
    }
    console.log('MongoDB connected');

    const db = client.db('loginApp');
    const collection = db.collection('users');

    // Create a POST route for handling login requests
    app.post('/login', async (req, res) => {
        try {
            const { username, password } = req.body;

            // Check if username and password are provided
            if (!username || !password) {
                return res.status(400).json({ error: 'Username and password are required' });
            }

            // Check if user exists in the database
            const user = await collection.findOne({ username });

            if (!user) {
                return res.status(404).json({ error: 'Invalid username or password' });
            }

            // Compare hashed password with stored password
            const passwordMatch = await bcrypt.compare(password, user.password);

            if (!passwordMatch) {
                return res.status(401).json({ error: 'Invalid username or password' });
            }

            // Return success response
            return res.status(200).json({ message: 'Login successful' });

        } catch (error) {
            console.error(error);
            res.status(500).json({ error: 'Server error' });
        }
    });

    // Start the server
    const PORT = process.env.PORT || 5000;
    app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
});
