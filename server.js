const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const helmet = require('helmet');
const morgan = require('morgan');
const rateLimit = require('express-rate-limit');

require('dotenv').config();

// Initialize express app
const app = express();

// Security Enhancements
app.use(helmet());
app.use(cors());

// Rate Limiting
const limiter = rateLimit({
    windowMs: 15 * 60 * 1000, // 15 minutes
    max: 100 // limit each IP to 100 requests per windowMs
});
app.use(limiter);

// Apply middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(morgan('dev')); // Use 'combined' for production

// MongoDB URI
const dbURI = process.env.MONGO_URI || 'mongodb://localhost/myapp';

// Connect to MongoDB
mongoose.connect(dbURI, {
    useNewUrlParser: true,
    useUnifiedTopology: true
})
.then(() => console.log('MongoDB connected successfully'))
.catch(err => console.error('MongoDB connection error:', err));

// Import routes
const userRoutes = require('./routes/users');

// Use Routes
app.use('/users', userRoutes);

// Root endpoint
app.get('/', (req, res) => {
    res.send('Hello from the server!');
});

// Error Handling Middleware
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send('Something broke!');
});

// Define a port and start server
const PORT = process.env.PORT || 5037;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));