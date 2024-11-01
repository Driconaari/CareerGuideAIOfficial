AI Career Coach
Welcome to AI Career Coach—your personal guide to career success! This application provides tailored career advice, skill assessments, and actionable insights to help users advance their careers using AI.

Table of Contents
Getting Started
Features
Setup Instructions
Environment Variables
Database Configuration
Contact
Getting Started
Prerequisites
Node.js (version 14 or above)
MySQL (version 8.0 or above)
npm or yarn for package management
Features
AI-Powered Insights: Personalized advice using AI to guide your career path.
Resume Review: Provides feedback to optimize your resume.
Skill Development: Identifies and suggests improvements for critical skills.
Progress Tracking: Visualizes your career growth.
Setup Instructions
1. Clone the Repository
Clone the repository to your local machine:

bash
Copy code
git clone [https://github.com/yourusername/ai-career-coach](https://github.com/Driconaari/CareerGuideAIOfficial.git)
cd ai-career-coach
2. Install Dependencies
Run the following command to install all required dependencies:

bash
Copy code
npm install
3. Set Up Environment Variables
Create a .env file in the root directory with the following variable:

plaintext
Copy code
OPENAI_API_KEY="your-api-key"
Replace "your-api-key" with your actual API key from OpenAI.

Database Configuration
Step 1: Start MySQL Server
Ensure that your MySQL server is running and accessible.

Step 2: Create the Database
Create a new schema called careerguide in MySQL:

sql
Copy code
CREATE SCHEMA careerguide;
Step 3: Database Initialization
After the careerguide schema is created, the application will set up the necessary tables automatically on first run.

Step 4: Set Up Database User (Optional)
To use a custom MySQL user, set up a .env file with the following additional variables:

plaintext
Copy code
DB_HOST="localhost"
DB_USER="your-username"
DB_PASSWORD="your-password"
DB_DATABASE="careerguide"
Running the Application
To start the server, run:


Contact
For further questions, issues, or support, feel free to reach out to asgervb@gmail.com.

Happy coding, and enjoy building your AI-powered career coach!
