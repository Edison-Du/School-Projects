import discord
import time

months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"]

class Time:
    def __init__(self, day, hours, minutes):
        self.day = day
        self.hours = hours
        self.minutes = minutes
    def printDate(self):
        hour = str(self.hours)
        minute = str(self.minutes)
        if (self.hours < 10):
            hour = "0" + str(self.hours)
        if (self.minutes < 10):
            minute = "0" + str(self.minutes)
        return ("%s:%s" % (hour, minute))


class Task:
    def __init__(self, name, day, hours, minutes):
        self.name = name
        self.curTime = Time(day, hours, minutes)
        
    def retMin(self):
        diff = 0
        diff += self.curTime.day*24
        diff += self.curTime.hours
        diff *= 60
        diff += self.curTime.minutes
        return diff


def cmp(task):
    return task.curTime.day, task.curTime.hours, task.curTime.minutes


class Schedule:
    def __init__(self):
        self.remind = 0
        self.taskList = []
        self.channel = None

    def addTask(self, newTask):
        self.taskList.append(newTask)
        self.taskList.sort(key=cmp)
        
    def deleteTask(self, index):
        index -= 1
        if index >= 0 and index < len(self.taskList):
            self.taskList.pop(index)
            return True
        return False
    
    def rescheduleTask(self, index, da, hr, mi):
        index -= 1
        if index < 0 or index >= len(self.taskList):
            return False
        self.taskList[index].curTime.day = da
        self.taskList[index].curTime.hours = hr
        self.taskList[index].curTime.minutes = mi
        self.taskList.sort(key=cmp)
        return True
    
    def scheduleEmbed(self, ctx):
        week = ["Mon", "Tues", "Wed", "Thur", "Fri", "Sat", "Sun"]
        mbed = discord.Embed (
            title = str(ctx.author)[:-5] + "'s Tasks",
            colour = discord.Colour.blue()
        )
        curDay = time.localtime().tm_wday
        tempDay = time.localtime().tm_mday
        cnt = 1

        for i in range(7):
            msg = ""
            for task in self.taskList:
                if task.curTime.day == tempDay:
                    msg += ("(%s) %s - %s\n" % (cnt, task.curTime.printDate(), task.name))
                    cnt += 1
            if (not msg == ""): 
                mbed.add_field(
                    name = week[curDay] + " (" + months[time.localtime().tm_mon - 1] + " " + str(tempDay) + ")",
                    value = msg,
                    inline = False
                )
            curDay = (curDay+1) % 7
            tempDay += 1
        
        msg = ""
        for task in self.taskList:
            if task.curTime.day > tempDay:
                msg += ("(%s) %s %s, %s - %s\n" % (cnt, months[time.localtime().tm_mon - 1], task.curTime.day, task.curTime.printDate(), task.name))
                cnt += 1
        if (not msg == ""): 
            mbed.add_field(
                name = "Future Upcoming Events",
                value = msg,
                inline = False
            )
        return mbed


        

    
        
        