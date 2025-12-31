const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());
app.use(express.static('public'));

const USERS_FILE = 'users.txt';
const ARTICLES_FILE = 'articles.txt';

// Helper to read users
function readUsers() {
    if (!fs.existsSync(USERS_FILE)) return {};
    const data = fs.readFileSync(USERS_FILE, 'utf8');
    const users = {};
    data.split('\n').forEach(line => {
        const parts = line.trim().split(',');
        if (parts.length >= 3) {
            users[parts[0]] = { id: parts[0], password: parts[1], role: parts[2] };
        }
    });
    return users;
}

// Helper to read articles
function readArticles() {
    if (!fs.existsSync(ARTICLES_FILE)) return [];
    const data = fs.readFileSync(ARTICLES_FILE, 'utf8');
    const articles = [];
    data.split('\n').forEach(line => {
        const parts = line.trim().split('|||'); // Assuming ||| as separator based on Java code
        if (parts.length >= 2) {
            articles.push({ name: parts[0], content: parts[1] });
        }
    });
    return articles;
}

// Helper to save articles
function saveArticles(articles) {
    const data = articles.map(a => `${a.name}|||${a.content}`).join('\n');
    fs.writeFileSync(ARTICLES_FILE, data);
}

// API: Login
app.post('/api/login', (req, res) => {
    const { id, password } = req.body;
    const users = readUsers();
    const user = users[id];

    if (user && user.password === password) {
        res.json({ success: true, role: user.role, id: user.id });
    } else {
        res.status(401).json({ success: false, message: 'Invalid credentials' });
    }
});

// API: Get Articles
app.get('/api/articles', (req, res) => {
    res.json(readArticles());
});

// API: Create Article
app.post('/api/articles', (req, res) => {
    const { name, content } = req.body;
    if (!name || !content) return res.status(400).json({ message: 'Missing fields' });
    
    const articles = readArticles();
    articles.push({ name, content });
    saveArticles(articles);
    res.json({ success: true, message: 'Article created' });
});

// API: Edit Article
app.put('/api/articles', (req, res) => {
    const { originalName, name, content } = req.body;
    const articles = readArticles();
    const index = articles.findIndex(a => a.name === originalName);
    
    if (index !== -1) {
        articles[index] = { name, content };
        saveArticles(articles);
        res.json({ success: true, message: 'Article updated' });
    } else {
        res.status(404).json({ message: 'Article not found' });
    }
});

// API: Delete Article
app.delete('/api/articles', (req, res) => {
    const { name } = req.body;
    const articles = readArticles();
    const newArticles = articles.filter(a => a.name !== name);
    
    if (articles.length !== newArticles.length) {
        saveArticles(newArticles);
        res.json({ success: true, message: 'Article deleted' });
    } else {
        res.status(404).json({ message: 'Article not found' });
    }
});

app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});
