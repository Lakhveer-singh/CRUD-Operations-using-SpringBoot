// script.js

const API_URL = "http://localhost:8080/api/contacts";

const fetchContacts = async () => {
    const response = await fetch(API_URL);
    if (!response.ok) {
        throw new Error("Failed to fetch contacts");
    }
    return await response.json();
};

const updateContact = async (id, updatedContact) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedContact),
    });

    if (!response.ok) {
        throw new Error("Failed to update contact");
    }

    return await response.json();
};

const deleteContact = async (id) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
    });

    if (!response.ok) {
        throw new Error("Failed to delete contact");
    }
};

const createContact = async (newContact) => {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newContact),
    });

    if (!response.ok) {
        throw new Error("Failed to create contact");
    }

    return await response.json();
};

const renderContacts = (contacts) => {
    const tbody = document.querySelector('#contact-table tbody');
    tbody.innerHTML = '';
    contacts.forEach(contact => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${contact.id}</td>
            <td>${contact.name}</td>
            <td>${contact.email}</td>
            <td>${contact.message}</td>
            <td>
                <button class="update" data-id="${contact.id}">Update</button>
                <button class="delete" data-id="${contact.id}">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });

    document.querySelectorAll('button.update').forEach(button => {
        button.addEventListener('click', async () => {
            const id = button.getAttribute('data-id');
            const newDetails = prompt("Enter the updated contact details (name, email, message) separated by commas:");
            if (newDetails) {
                const [name, email, message] = newDetails.split(',');
                try {
                    const updatedContact = await updateContact(id, { name, email, message });
                    contacts = contacts.map(contact => contact.id === id ? updatedContact : contact);
                    renderContacts(contacts);
                } catch (error) {
                    alert(error.message);
                }
            }
        });
    });

    document.querySelectorAll('button.delete').forEach(button => {
        button.addEventListener('click', async () => {
            const id = button.getAttribute('data-id');
            if (confirm("Are you sure you want to delete this contact?")) {
                try {
                    await deleteContact(id);
                    contacts = contacts.filter(contact => contact.id !== id);
                    renderContacts(contacts);
                } catch (error) {
                    alert(error.message);
                }
            }
        });
    });
};

document.getElementById('add-contact-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const message = document.getElementById('message').value;
    try {
        const newContact = await createContact({ name, email, message });
        const contacts = await fetchContacts();
        renderContacts(contacts);
        document.getElementById('add-contact-form').reset();
    } catch (error) {
        alert(error.message);
    }
});

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const contacts = await fetchContacts();
        renderContacts(contacts);
    } catch (error) {
        alert(error.message);
    }
});
