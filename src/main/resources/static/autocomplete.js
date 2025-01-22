const input = document.getElementById('address-input');
const autocompleteList = document.getElementById('autocomplete-list');
const apiKey = 'ab9ad5ad-746e-49e2-ae92-ced835db32eb'; // Вставьте ваш API ключ

input.addEventListener('input', async () => {
    const query = input.value.trim();

    if (query.length < 3) {
        autocompleteList.hidden = true;
        return;
    }

    try {
        const response = await fetch(`https://suggest-maps.yandex.ru/v1/suggest?apikey=${apiKey}&text=${encodeURIComponent(query)}&lang=ru&results=5`);
        const data = await response.json();

        renderSuggestions(data.results || []);
    } catch (error) {
        console.error('Ошибка при загрузке подсказок:', error);
    }
});

function renderSuggestions(suggestions) {
    autocompleteList.innerHTML = '';
    autocompleteList.hidden = suggestions.length === 0;

    suggestions.forEach(suggestion => {
        const item = document.createElement('div');
        item.className = 'autocomplete-item';
        item.textContent = suggestion.title.text;

        item.addEventListener('click', () => {
            input.value = suggestion.title.text;
            autocompleteList.hidden = true;
        });

        autocompleteList.appendChild(item);
    });
}

document.addEventListener('click', (e) => {
    if (!autocompleteList.contains(e.target) && e.target !== input) {
        autocompleteList.hidden = true;
    }
});
