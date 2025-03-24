def letter_to_points(grade):
    """
    Convert a letter grade to its corresponding grade point.
    Supports common letter grades; returns None if the grade is invalid.
    """
    grade_points = {
        "A+": 4.0,
        "A": 4.0,
        "A-": 3.7,
        "B+": 3.3,
        "B": 3.0,
        "B-": 2.7,
        "C+": 2.3,
        "C": 2.0,
        "C-": 1.7,
        "D+": 1.3,
        "D": 1.0,
        "D-": 0.7,
        "F": 0.0
    }
    return grade_points.get(grade.strip().upper())

def calculate_gpa(courses):
    """
    Calculate the GPA given a list of courses.
    Each course is a tuple: (grade_point, credit_hours)
    """
    total_points = 0.0
    total_credits = 0.0
    for grade_point, credits in courses:
        total_points += grade_point * credits
        total_credits += credits
    return total_points / total_credits if total_credits > 0 else 0.0

def main():
    print("Welcome to the GPA Calculator!")
    courses = []
    
    while True:
        grade_input = input("Enter course letter grade (e.g., A, B+, C-): ")
        grade_point = letter_to_points(grade_input)
        if grade_point is None:
            print("Invalid grade input. Please enter a valid letter grade (e.g., A, B+, C-).")
            continue
        
        try:
            credits = float(input("Enter course credit hours: "))
        except ValueError:
            print("Invalid input for credit hours. Please enter a numeric value.")
            continue
        
        courses.append((grade_point, credits))
        
        cont = input("Do you want to add another course? (y/n): ").strip().lower()
        if cont != 'y':
            break

    gpa = calculate_gpa(courses)
    print(f"Your GPA is: {gpa:.2f}")

if __name__ == "__main__":
    main()
