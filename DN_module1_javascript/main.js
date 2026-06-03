// ========== 1. BASICS & SETUP ==========
console.log("Welcome to the Community Portal");
window.addEventListener("load", () => {
  alert("Page fully loaded! Enjoy exploring events.");
});

// ========== 2. DATA TYPES & OPERATORS ==========
const EVENT_NAME = "Summer Concert";
const EVENT_DATE = "2025-07-15";
let seatsAvailable = 50;
document.getElementById("seatCount").innerText = seatsAvailable;
document.getElementById("incSeat").onclick = () => {
  if (seatsAvailable > 0) seatsAvailable--;
  document.getElementById("seatCount").innerText = seatsAvailable;
  console.log(`Registered for ${EVENT_NAME} on ${EVENT_DATE}, seats left: ${seatsAvailable}`);
};
document.getElementById("decSeat").onclick = () => {
  seatsAvailable++;
  document.getElementById("seatCount").innerText = seatsAvailable;
  console.log(`Cancelled registration, seats now: ${seatsAvailable}`);
};

// ========== 3. CONDITIONALS, LOOPS, ERROR HANDLING ==========
// Will be used when rendering events: hide past/full events, try-catch around registration

// ========== 4. FUNCTIONS, CLOSURES, HIGHER-ORDER ==========
let totalRegistrationsByCategory = (function() {
  let counts = { music: 0, art: 0, food: 0, sports: 0 };
  return {
    increment: (cat) => { if (counts[cat] !== undefined) counts[cat]++; console.log(`Total ${cat} registrations: ${counts[cat]}`); },
    getCounts: () => ({ ...counts })
  };
})();

function addEvent(eventsArray, newEvent) { eventsArray.push(newEvent); }
function registerUser(eventName) { console.log(`User registered for ${eventName}`); }
function filterEventsByCategory(events, category, callback) {
  const filtered = category === "all" ? events : events.filter(e => e.category === category);
  callback(filtered);
}

// ========== 5. OBJECTS & PROTOTYPES ==========
class Event {
  constructor(id, name, date, category, seats, description) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.category = category;
    this.seats = seats;
    this.description = description;
  }
  checkAvailability() { return this.seats > 0; }
}
// Add prototype method
Event.prototype.getInfo = function() {
  return `${this.name} - ${this.date} (${this.seats} seats left)`;
};

// ========== 6. ARRAYS & METHODS ==========
let eventsArray = []; // will be populated from fetch
function addNewEvent(eventObj) { eventsArray.push(eventObj); }
function getMusicEvents() { return eventsArray.filter(e => e.category === "music"); }
function formatEventCards() { return eventsArray.map(e => `${e.name} (${e.category})`); }

// ========== 7. DOM MANIPULATION ==========
const eventsContainer = document.getElementById("eventsContainer");
function renderEvents(eventsToRender) {
  eventsContainer.innerHTML = "";
  if (!eventsToRender.length) {
    eventsContainer.innerHTML = "<p>No events match your criteria.</p>";
    return;
  }
  eventsToRender.forEach(event => {
    const card = document.createElement("div");
    card.className = "event-card";
    card.setAttribute("data-id", event.id);
    card.innerHTML = `
      <h3>${event.name}</h3>
      <p><strong>Date:</strong> ${event.date}</p>
      <p><strong>Category:</strong> ${event.category}</p>
      <p><strong>Seats:</strong> ${event.seats}</p>
      <p>${event.description}</p>
      <button class="registerBtn">📝 Register</button>
      <button class="cancelBtn">❌ Cancel</button>
    `;
    // Attach event handlers
    const regBtn = card.querySelector(".registerBtn");
    regBtn.onclick = () => handleRegistration(event);
    const cancelBtn = card.querySelector(".cancelBtn");
    cancelBtn.onclick = () => handleCancellation(event);
    eventsContainer.appendChild(card);
  });
}

// Helper to update UI after registration/cancellation
function updateEventInArray(eventId, newSeats) {
  const ev = eventsArray.find(e => e.id === eventId);
  if (ev) ev.seats = newSeats;
  // Re-render with current filters
  applyFiltersAndSearch();
}

function handleRegistration(event) {
  try {
    if (!event.checkAvailability()) throw new Error("No seats available for this event.");
    event.seats--;
    updateEventInArray(event.id, event.seats);
    registerUser(event.name);
    totalRegistrationsByCategory.increment(event.category);
    alert(`✅ Registered for ${event.name}! Seats left: ${event.seats}`);
    console.log(Object.entries(event)); // task 5: Object.entries
  } catch (error) {
    console.error("Registration error:", error);
    alert(error.message);
  }
}

function handleCancellation(event) {
  event.seats++;
  updateEventInArray(event.id, event.seats);
  alert(`❌ Cancelled registration for ${event.name}. Seats now: ${event.seats}`);
}

// ========== 8. EVENT HANDLING (onchange, keydown) ==========
const categoryFilter = document.getElementById("categoryFilter");
const searchInput = document.getElementById("searchInput");
let currentEvents = [];

function applyFiltersAndSearch() {
  let filtered = [...eventsArray];
  const category = categoryFilter.value;
  if (category !== "all") filtered = filtered.filter(e => e.category === category);
  const searchTerm = searchInput.value.toLowerCase();
  if (searchTerm) filtered = filtered.filter(e => e.name.toLowerCase().includes(searchTerm));
  renderEvents(filtered);
  document.getElementById("searchInfo").innerText = searchTerm ? `🔍 ${filtered.length} matches` : "";
}

categoryFilter.onchange = applyFiltersAndSearch;
searchInput.addEventListener("keydown", (e) => {
  if (e.key === "Enter") applyFiltersAndSearch();
});
searchInput.addEventListener("input", applyFiltersAndSearch); // real-time

// ========== 9. ASYNC JS: FETCH + PROMISES + ASYNC/AWAIT + SPINNER ==========
async function fetchEventsFromAPI() {
  eventsContainer.innerHTML = '<div class="spinner"></div>';
  try {
    // Mock API endpoint (JSONPlaceholder returns dummy data, we map to our event structure)
    const response = await fetch("https://jsonplaceholder.typicode.com/posts?_limit=6");
    if (!response.ok) throw new Error("Network error");
    const data = await response.json();
    // Transform mock data into Event objects
    const mockEvents = data.map((item, idx) => new Event(
      idx,
      ["Summer Jazz", "Art Expo", "Food Truck Rally", "Marathon", "Painting Workshop", "Rock Concert"][idx],
      `2025-0${Math.floor(Math.random() * 6) + 6}-${Math.floor(Math.random() * 28) + 1}`,
      ["music", "art", "food", "sports", "art", "music"][idx],
      Math.floor(Math.random() * 50) + 10,
      `Join us for ${["great music", "creative art", "delicious food", "sports fun", "art session", "live band"][idx]}.`
    ));
    eventsArray = mockEvents;
    currentEvents = [...eventsArray];
    renderEvents(eventsArray);
    populateEventSelect();
    console.log("Events loaded via async/await");
  } catch (error) {
    console.error("Fetch error:", error);
    eventsContainer.innerHTML = "<p>Failed to load events. Please refresh.</p>";
  }
}

// ========== 10. MODERN JS: destructuring, default params ==========
function displayEventDetails({ name, date, seats } = { name: "Unknown", date: "TBD", seats: 0 }) {
  console.log(`Event: ${name}, Date: ${date}, Seats: ${seats}`);
}
// Example usage
displayEventDetails(eventsArray[0] || {});

// ========== 11. WORKING WITH FORMS ==========
const regForm = document.getElementById("registrationForm");
const regEventSelect = document.getElementById("regEventSelect");
function populateEventSelect() {
  regEventSelect.innerHTML = '<option value="">-- Choose an event --</option>';
  eventsArray.forEach(event => {
    const option = document.createElement("option");
    option.value = event.id;
    option.textContent = `${event.name} (${event.date}) - ${event.seats} seats`;
    regEventSelect.appendChild(option);
  });
}
regForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const name = document.getElementById("regName").value.trim();
  const email = document.getElementById("regEmail").value.trim();
  const eventId = regEventSelect.value;
  // validation
  let valid = true;
  document.getElementById("nameError").innerText = "";
  document.getElementById("emailError").innerText = "";
  document.getElementById("eventError").innerText = "";
  if (!name) { document.getElementById("nameError").innerText = "Name required"; valid = false; }
  if (!email.includes("@")) { document.getElementById("emailError").innerText = "Valid email required"; valid = false; }
  if (!eventId) { document.getElementById("eventError").innerText = "Select an event"; valid = false; }
  if (!valid) return;

  const selectedEvent = eventsArray.find(e => e.id == eventId);
  if (!selectedEvent || selectedEvent.seats <= 0) {
    alert("Sorry, this event is fully booked.");
    return;
  }
  // ========== 12. AJAX & FETCH: POST to mock API with setTimeout ==========
  const formMessage = document.getElementById("formMessage");
  formMessage.innerHTML = "Processing registration...";
  try {
    const response = await fetch("https://jsonplaceholder.typicode.com/posts", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, eventId: selectedEvent.id, eventName: selectedEvent.name })
    });
    // simulate delay
    await new Promise(resolve => setTimeout(resolve, 1500));
    if (response.ok) {
      // reduce seats
      selectedEvent.seats--;
      updateEventInArray(selectedEvent.id, selectedEvent.seats);
      populateEventSelect();
      formMessage.innerHTML = "✅ Registration successful! Check your email.";
      console.log("POST payload sent:", { name, email, event: selectedEvent.name });
    } else {
      throw new Error("Server error");
    }
  } catch (err) {
    formMessage.innerHTML = "❌ Registration failed. Please try again.";
    console.error("Fetch POST error:", err);
  }
});

// ========== 13. DEBUGGING (logs, breakpoints suggestion) ==========
console.log("Debug: event array initially", eventsArray);
// In DevTools, you can set breakpoints inside handleRegistration or fetchEventsFromAPI

// ========== 14. JQUERY & FRAMEWORKS ==========
$(document).ready(function() {
  $("#incSeat, #decSeat").click(function() {
    $(this).fadeOut(100).fadeIn(100); // simple jQuery effect
  });
  // fadeOut/fadeIn on event cards via jQuery when filtering? For demo, add a hover effect
  $(document).on("mouseenter", ".event-card", function() { $(this).fadeTo("fast", 0.95); });
  $(document).on("mouseleave", ".event-card", function() { $(this).fadeTo("fast", 1); });
  console.log("jQuery ready: fade effects added.");
  // Mention benefit of frameworks: "React/Vue provide component-based architecture and easier state management."
});
// One benefit of moving to React or Vue: they offer reactive data binding and virtual DOM for better performance and maintainability.

// Initialize everything
fetchEventsFromAPI();