tasks {
    task {
        id = '2_2_1'
        name = 'Простые числа'
        maxPoints = 1
        softDeadline = '14/02/2025'
        hardDeadline = '21/02/2025'
    }
    task {
        id = '2_2_1'
        name = 'Пиццерия'
        maxPoints = 1
        softDeadline = '07/03/2025'
        hardDeadline = '14/03/2025'
    }
    task {
        id = '2_3_1'
        name = 'Змейка'
        maxPoints = 1
        softDeadline = '28/03/2025'
        hardDeadline = '04/04/2025'
    }
    task{
        id = '2_4_1'
        name = 'Автоматическая проверка задач по ООП'
        maxPoints = 1
        softDeadline = '18/04/2025'
        hardDeadline = '02/05/2025'
    }
    task{
        id = '2_1_2'
        name = 'Простые числа'
        maxPoints = 1
        softDeadline = '16/05/2025'
        hardDeadline = '30/05/2025'
    }
}

groups {
    group {
        name = '23216'
        students {
            student {
                github = 'ttomilov'
                fullName = 'Томилов Тимофей Максимович'
                repo = 'https://github.com/ttomilov/OOP'
            }
            student {
                github = 'nkrainov'
                fullName = 'Крайнов Никита Сергеевич'
                repo = 'https://github.com/nkrainov/OOP'
            }
        }
    }
}

settings {
    gradeRules {
        rule { min = 5; grade = 'Отлично' }
        rule { min = 4; grade = 'Хорошо' }
        rule { min = 3; grade = 'Удовлетворительно' }
        rule { min = 0;  grade = 'Неудовлетворительно' }
    }
}
