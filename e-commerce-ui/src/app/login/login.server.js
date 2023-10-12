const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const app = express();
mongoose.connect('mongodb://localhost:4200', { useNewUrlParser: true, useUnifiedTopology: true });
const User = mongoose.model('User', new mongoose.Schema({ email: String }));
app.use(cors());
app.use(express.json());
app.post('/api/register', async (req, res) => {
    try {
        const { email } = req.body;
        if (!email) return res.status(400).send('Email is required');
        
        const user = new User({ email });
        await user.save();

        res.send('User registered successfully');
    } catch (error) {
        console.error(error);
        res.status(500).send('Internal server error');
    }
});
app.listen(4200, () => console.log('Server is running on http://localhost:4200'));