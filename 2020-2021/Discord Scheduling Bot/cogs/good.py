import discord
from discord.ext import tasks, commands
import random
import json
import os
import time
import classes

class Good(commands.Cog):
    def __init__(self, bot): 
        self.bot = bot
        self.dirPath = os.path.dirname(os.path.realpath(__file__))

    def getText(self, task):
        msg = str(task.curTime.day) + " " + str(task.curTime.hours) + " " + str(task.curTime.minutes) + " " + task.name + "\n"
        return msg
    
    def correct(self, da, hr, mi):
        curTime = time.localtime()
        if (da < 1): return False
        if (curTime.tm_mon == 2 and da > 28): return False
        if (curTime.tm_mon % 2 == 0 and da > 30): return False
        if (curTime.tm_mon % 2 == 1 and da > 31): return False
        if (hr < 0 or hr > 23): return False
        if (mi < 0 or mi > 59): return False
        if (da*1440+hr*60+mi <= curTime.tm_mday*1440+curTime.tm_hour*60+curTime.tm_min): return False
        return True

    def convert(self, minutes):

        if (minutes == 0): return "0m"

        days = minutes//1440
        minutes -= days*1440
        hours = minutes//60
        minutes -= hours*60

        ret = ""
        if (days > 0): ret += str(days) + "d "
        if (hours > 0): ret += str(hours) + "h "
        if (minutes > 0): ret += str(minutes) + "m "
        return ret

    @commands.command(name = "schedule")
    async def sched(self, ctx, name, da, hr, mi):
        # handle invalid input
        try:
            da = int(da)
            hr = int(hr)
            mi = int(mi)
        except:
            await ctx.send("Invalid parameters.")
            return

        if (not self.correct(da, hr ,mi)):
            await ctx.send("Invalid parameters.")
            return

        temp = classes.Task(name, da, hr ,mi)

        userid = str(ctx.author.id)

        if userid not in self.bot.userSchedules:
            self.bot.userSchedules[userid] = classes.Schedule()

        self.bot.userSchedules[userid].addTask(temp)

        await ctx.send(f"{ctx.author.mention} Task succesfully added.")


    @commands.command(name = "delete")
    async def delete(self, ctx, index):
        try:
            index = int(index)
        except:
            await ctx.send("Invalid parameters.")
            return
        
        userid = str(ctx.author.id)

        if userid not in self.bot.userSchedules:
            self.bot.userSchedules[userid] = classes.Schedule()

        if (self.bot.userSchedules[userid].deleteTask(index)):
            await ctx.send(f"{ctx.author.mention} Task succesfully deleted.")
        else:
            await ctx.send(f"{ctx.author.mention} No such task exists.")


    @commands.command(name = "move")
    async def resched(self, ctx, index, da, hr, mi):
        try:
            index = int(index)
            da = int(da)
            hr = int(hr)
            mi = int(mi)
        except:
            await ctx.send("Invalid parameters.")
            return

        if (not self.correct(da, hr ,mi)):
            await ctx.send("Invalid parameters.")
            return

        userid = str(ctx.author.id)

        if userid not in self.bot.userSchedules:
            self.bot.userSchedules[userid] = classes.Schedule()

        if (self.bot.userSchedules[userid].rescheduleTask(index, da, hr, mi)):
            await ctx.send(f"{ctx.author.mention} Task succesfully rescheduled.")
        else:
            await ctx.send(f"{ctx.author.mention} No such task exists.")



    @commands.command(name = "all")
    async def showSched(self, ctx):

        userid = str(ctx.author.id)
        if userid not in self.bot.userSchedules:
            self.bot.userSchedules[userid] = classes.Schedule()

        if (len(self.bot.userSchedules[userid].taskList) == 0):
            await ctx.send(f"{ctx.author.mention} No pending tasks.")
        else:
            mbed = self.bot.userSchedules[userid].scheduleEmbed(ctx)
            await ctx.send(embed = mbed)
    
    @commands.command(name = "setremind")
    async def setRemind(self, ctx, amt):
        try:
            val = int(amt[0:-1])
            c = amt[-1]
        except:
            await ctx.send("Invalid Input")
            return
        
        if (c == 'd' or c == 'D'): val *= 1440
        elif (c == 'h' or c == 'H'): val *= 60
        elif (not c == 'm' and not c == 'M'):
            await ctx.send("Invalid Input")
            return

        userid = str(ctx.author.id)
        if userid not in self.bot.userSchedules:
            self.bot.userSchedules[userid] = classes.Schedule()
        
        self.bot.userSchedules[userid].remind = val

        await ctx.send(f"{ctx.author.mention} Reminder time succesfully changed.")
        
    @commands.command(name = "remindtime")
    async def remindTime(self, ctx):
        userid = str(ctx.author.id)
        if userid not in self.bot.userSchedules:
            self.bot.userSchedules[userid] = classes.Schedule()
        
        val = self.bot.userSchedules[userid].remind
        await ctx.send(f"{ctx.author.mention} Your reminder is set to " + self.convert(val))
        
    @commands.command(name = "setchannel")
    async def setChannel(self, ctx):
        userid = str(ctx.author.id)
        if userid not in self.bot.userSchedules:
            self.bot.userSchedules[userid] = classes.Schedule()
        self.bot.userSchedules[userid].channel = ctx.channel
        await ctx.send(f"{ctx.author.mention} Reminder channel changed to this channel")

def setup(bot): 
    bot.add_cog(Good(bot))