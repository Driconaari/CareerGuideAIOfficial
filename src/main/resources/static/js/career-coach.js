document.addEventListener('DOMContentLoaded', function() {
    // Skill recommendation chart
    var skillCtx = document.getElementById('skillChart').getContext('2d');
    var skillChart = new Chart(skillCtx, {
        type: 'radar',
        data: {
            labels: ['JavaScript', 'Python', 'Java', 'SQL', 'Machine Learning'],
            datasets: [{
                label: 'Your Skills',
                data: [65, 59, 90, 81, 56],
                fill: true,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                pointBackgroundColor: 'rgb(255, 99, 132)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgb(255, 99, 132)'
            }, {
                label: 'Industry Average',
                data: [70, 65, 75, 80, 60],
                fill: true,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgb(54, 162, 235)',
                pointBackgroundColor: 'rgb(54, 162, 235)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgb(54, 162, 235)'
            }]
        },
        options: {
            elements: {
                line: {
                    borderWidth: 3
                }
            }
        }
    });

    // Career progress chart
    var progressCtx = document.getElementById('progressChart').getContext('2d');
    var progressChart = new Chart(progressCtx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
            datasets: [{
                label: 'Career Progress',
                data: [12, 19, 3, 5, 2, 3],
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Form submission handling
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(this);
            fetch(this.action, {
                method: 'POST',
                body: formData
            })
                .then(response => response.text())
                .then(html => {
                    const parser = new DOMParser();
                    const doc = parser.parseFromString(html, 'text/html');
                    const newContent = doc.querySelector(this.closest('section').id);
                    if (newContent) {
                        this.closest('section').innerHTML = newContent.innerHTML;
                    }
                })
                .catch(error => console.error('Error:', error));
        });
    });
});