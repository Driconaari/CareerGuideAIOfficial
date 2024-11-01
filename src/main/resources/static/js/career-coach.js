document.addEventListener('DOMContentLoaded', function() {
    // Skill chart
    fetch('/api/skill-progress')
        .then(response => response.json())
        .then(data => {
            const labels = data.map(item => item.skillName);
            const values = data.map(item => item.proficiencyLevel);

            var skillCtx = document.getElementById('skillChart').getContext('2d');
            var skillChart = new Chart(skillCtx, {
                type: 'radar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Your Skills',
                        data: values,
                        fill: true,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgb(255, 99, 132)',
                        pointBackgroundColor: 'rgb(255, 99, 132)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgb(255, 99, 132)'
                    }]
                },
                options: {
                    elements: {
                        line: {
                            borderWidth: 3
                        }
                    },
                    scales: {
                        r: {
                            angleLines: {
                                display: false
                            },
                            suggestedMin: 0,
                            suggestedMax: 5
                        }
                    }
                }
            });
        });

    // Career progress chart
    var progressCtx = document.getElementById('progressChart').getContext('2d');
    var progressChart = new Chart(progressCtx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
            datasets: [{
                label: 'Career Progress',
                data: [1, 2, 2, 3, 3, 4],
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    max: 5
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