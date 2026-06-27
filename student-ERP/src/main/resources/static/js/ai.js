async function askAI() {
    const input = document.getElementById('question');
    const question = input.value.trim();
    if (!question) return;

    addMessage('You', question);
    input.value = '';

    try {
        const res = await fetch('/api/ai/' + encodeURIComponent(question));
        if (!res.ok) throw new Error('AI service returned status ' + res.status);
        const text = await res.text();
        addMessage('AI', text);
    } catch (e) {
        addMessage('AI', 'Error: ' + e.message);
    }
}

function askQuick(question) {
    document.getElementById('question').value = question;
    askAI();
}

async function loadAtRisk() {
    document.getElementById('question').value = 'Which students are at risk due to low attendance below 75%?';
    await askAI();
}

function addMessage(sender, text) {
    const box = document.getElementById('chat-box');
    const div = document.createElement('div');
    div.innerHTML = '<strong>' + sender + ':</strong> ' + text;
    div.style.marginBottom = '8px';
    box.appendChild(div);
    box.scrollTop = box.scrollHeight;
}
