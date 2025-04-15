document.getElementById("fileInput").addEventListener("change", function (e) {
    const file = e.target.files[0];
    const reader = new FileReader();
  
    reader.onload = function (e) {
      const text = e.target.result;
      const rows = text.trim().split("\n").slice(0, 5);
      const previewHTML = rows.map(row => `<div>${row}</div>`).join("");
      document.getElementById("previewContainer").innerHTML = `<h3>Preview:</h3>${previewHTML}`;
    };
  
    if (file) {
      reader.readAsText(file);
    }
  });
  
  document.getElementById("uploadForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];
  
    if (!file) {
      alert("Please select a CSV file.");
      return;
    }
  
    const formData = new FormData();
    formData.append("file", file);
  
    fetch("/upload", {
      method: "POST",
      body: formData,
    })
    .then(res => res.text())
    .then(data => {
      document.getElementById("responseMessage").innerText = data;
    })
    .catch(err => {
      document.getElementById("responseMessage").innerText = "Error uploading file.";
    });
  });
  