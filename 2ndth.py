import random

lists = ["stone", "paper", "scissor"]
ai_points, user_points = 0, 0


def chance(ai_choice, user_choice):
    global ai_points, user_points
    if ai_choice == user_choice:
        print("\t\t\t\tboth are same")
    elif ai_choice == "paper" and user_choice == "stone":
        ai_points = ai_points + 1
    elif ai_choice == "paper" and user_choice == "scissor":
        user_points = user_points + 1
    elif ai_choice == "stone" and user_choice == "paper":
        user_points = user_points + 1
    elif ai_choice == "stone" and user_choice == "scissor":
        ai_points = ai_points + 1
    elif ai_choice == "scissor" and user_choice == "paper":
        ai_points = ai_points + 1
    elif ai_choice == "scissor" and user_choice == "stone":
        user_points = user_points + 1
    return ai_points, user_points


def initialize():
    ai_choice = random.choice(lists)
    user_choice = input("you choose:")
    print("ai choosed:{}".format(ai_choice))
    return ai_choice, user_choice


def takeoff():
    global ai_points, user_points
    match_fix = int(input("\t\t\t\t\tMatch fix:"))
    counter = 0
    while counter < match_fix:
        ai_choice, user_choice = initialize()
        ai_points, user_points = chance(ai_choice, user_choice)
        counter += 1
        print("\t\tai points:{}\t\tuser points:{}\n".format(ai_points, user_points))
    print("End of game")
    if user_points > ai_points:
        print("Hurray!!! you won the game")
    elif user_points == ai_points:
        print("draw!!!!")
    else:
        print("ohhh!! The AI won the game")
    print("\t\t\tFinal score\nuser points={}\tAI points={}".format(user_points, ai_points))
    return


def mains():
    takeoff()
    global ai_points, user_points
    ai_points, user_points = 0, 0
    print("To try again press 1 or 0 to exit")
    a = int(input())
    if a == 1:
        mains()
    else:
        print("exiting........")
        return


print("\t\t\t\t\t\t\t\t\t\t############################# Lets play....Stone....Paper....Scissors "
      "#############################")
mains()
