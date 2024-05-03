const express = require('express');
const router = express.Router();

// Get all users
router.get('/', (req, res) => {
    res.send('Respond with a resource of users');
});

// Get a single user by id
router.get('/:id', (req, res) => {
    res.send(`Retrieve user with ID ${req.params.id}`);
});

// Create a new user
router.post('/', (req, res) => {
    res.send('Create a new user');
});

// Update an existing user
router.put('/:id', (req, res) => {
    res.send(`Update user with ID ${req.params.id}`);
});

// Delete a user
router.delete('/:id', (req, res) => {
    res.send(`Delete user with ID ${req.params.id}`);
});

module.exports = router;