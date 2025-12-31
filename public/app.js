const API_URL = 'http://localhost:3000/api';

// DOM Elements
const loginSection = document.getElementById('login-section');
const dashboardSection = document.getElementById('dashboard-section');
const loginForm = document.getElementById('login-form');
const logoutBtn = document.getElementById('logout-btn');
const userDisplay = document.getElementById('user-display');
const articlesGrid = document.getElementById('articles-grid');
const createBtn = document.getElementById('create-btn');
const articleModal = document.getElementById('article-modal');
const modalTitle = document.getElementById('modal-title');
const cancelModalBtn = document.getElementById('cancel-modal');
const saveArticleBtn = document.getElementById('save-article');
const articleNameInput = document.getElementById('article-name');
const articleContentInput = document.getElementById('article-content');
const editOriginalNameInput = document.getElementById('edit-original-name');

// State
let currentUser = null;

// Event Listeners
loginForm.addEventListener('submit', handleLogin);
logoutBtn.addEventListener('click', handleLogout);
createBtn.addEventListener('click', openCreateModal);
cancelModalBtn.addEventListener('click', closeModal);
saveArticleBtn.addEventListener('click', saveArticle);

// Check if already logged in (optional, for persistent sessions - skip for simplicity)

async function handleLogin(e) {
    e.preventDefault();
    const id = document.getElementById('userid').value;
    const password = document.getElementById('password').value;

    try {
        const res = await fetch(`${API_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id, password })
        });
        const data = await res.json();

        if (data.success) {
            currentUser = { id: data.id, role: data.role };
            showDashboard();
        } else {
            alert(data.message);
        }
    } catch (err) {
        console.error(err);
        alert('Login failed. Is the server running?');
    }
}

function handleLogout() {
    currentUser = null;
    loginSection.classList.remove('hidden');
    dashboardSection.classList.add('hidden');
    loginForm.reset();
}

function showDashboard() {
    loginSection.classList.add('hidden');
    dashboardSection.classList.remove('hidden');
    userDisplay.textContent = `${currentUser.id} (${currentUser.role})`;

    // Show/Hide controls based on role
    // Admin and Faculty can Create/Edit/Delete
    if (['Admin', 'Faculty'].includes(currentUser.role)) {
        createBtn.classList.remove('hidden');
    } else {
        createBtn.classList.add('hidden');
    }

    loadArticles();
}

async function loadArticles() {
    try {
        const res = await fetch(`${API_URL}/articles`);
        const articles = await res.json();
        renderArticles(articles);
    } catch (err) {
        console.error(err);
    }
}

function renderArticles(articles) {
    articlesGrid.innerHTML = '';
    articles.forEach(article => {
        const card = document.createElement('div');
        card.className = 'article-card';

        let actionsHtml = '';
        if (currentUser && ['Admin', 'Faculty'].includes(currentUser.role)) {
            actionsHtml = `
                <div class="card-actions">
                    <button class="btn-icon" onclick="openEditModal('${article.name.replace(/'/g, "\\'")}', '${article.content.replace(/'/g, "\\'").replace(/\n/g, "\\n")}')">
                        <i class="fa-solid fa-pen"></i>
                    </button>
                    <button class="btn-icon delete" onclick="deleteArticle('${article.name.replace(/'/g, "\\'")}')">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </div>
            `;
        }

        card.innerHTML = `
            <div class="article-title">${article.name}</div>
            <div class="article-content">${article.content}</div>
            ${actionsHtml}
        `;
        articlesGrid.appendChild(card);
    });
}

// Modal Functions
function openCreateModal() {
    modalTitle.textContent = 'New Article';
    articleNameInput.value = '';
    articleContentInput.value = '';
    editOriginalNameInput.value = '';
    articleModal.classList.remove('hidden');
}

// Global functions for inline onclick (dirty but works for fast proto)
window.openEditModal = (name, content) => {
    modalTitle.textContent = 'Edit Article';
    articleNameInput.value = name;
    articleContentInput.value = content;
    editOriginalNameInput.value = name;
    articleModal.classList.remove('hidden');
};

function closeModal() {
    articleModal.classList.add('hidden');
}

async function saveArticle() {
    const name = articleNameInput.value;
    const content = articleContentInput.value;
    const originalName = editOriginalNameInput.value;

    if (!name || !content) return alert('Please fill all fields');

    const method = originalName ? 'PUT' : 'POST';
    const body = originalName ? { originalName, name, content } : { name, content };

    try {
        const res = await fetch(`${API_URL}/articles`, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });

        if (res.ok) {
            closeModal();
            loadArticles();
        } else {
            alert('Failed to save article');
        }
    } catch (err) {
        console.error(err);
    }
}

window.deleteArticle = async (name) => {
    if (!confirm(`Delete article "${name}"?`)) return;

    try {
        const res = await fetch(`${API_URL}/articles`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name })
        });

        if (res.ok) {
            loadArticles();
        } else {
            alert('Failed to delete article');
        }
    } catch (err) {
        console.error(err);
    }
};
