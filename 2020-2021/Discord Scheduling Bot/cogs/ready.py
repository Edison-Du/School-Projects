import discord
from discord.ext import tasks, commands
import json
import os
import time

months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"]

class Ready(commands.Cog):
    def __init__(self, bot): 
        self.bot = bot
        self.dirPath = os.path.dirname(os.path.realpath(__file__))

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

    @commands.Cog.listener()
    async def on_ready(self): 
        
        print("Online")

        self.bot.userSchedules = {}

        if (not self.autodelete.is_running()):
            self.autodelete.start()

        if (not self.autoremind.is_running()):
            self.autoremind.start()

    @commands.command()
    async def help(self, ctx):

        msg = ""
        msg += "!schedule <task name> <day> <hour> <minute> - Schedule event on a time\n"
        msg += "!delete <task #> - Delete specified task\n"
        msg += "!move <task #> <day> <hour> <minute> - Move specified task to specified time\n"
        msg += "!all - Display all tasks\n"
        msg += "!setremind <# of unit (m, h, or d)> - Set the reminder time for tasks\n"
        msg += "!setchannel - Set the reminder channel to the current channel\n"
        msg += "!remindtime - Display the current reminder time"

        mbed = discord.Embed(
            title = "Schedule Bot",
            description = "Schedule bot used for scheduling events (Default reminder is 0 minutes)",
            color = discord.Colour.gold()
        )

        mbed.add_field(
            name = "Commands",
            value = msg,
            inline = False
        )

        await ctx.send(embed = mbed)

    @commands.command()
    async def shutdown(self, ctx):
        await ctx.send("Shutting Down.")
        await self.bot.close()

    @shutdown.error
    async def shutdown_error(self, ctx, error):
        if isinstance(error, (commands.CommandError)):
            await ctx.send(f"{ctx.author.mention} You are not allowed to use owner commands.")

    @tasks.loop(minutes=1.0)
    async def autoremind(self):
        curTime = time.localtime()
        for j in self.bot.userSchedules:
            for i in range (len(self.bot.userSchedules[j].taskList)):
                da = self.bot.userSchedules[j].taskList[i].curTime.day
                hr = self.bot.userSchedules[j].taskList[i].curTime.hours
                mi = self.bot.userSchedules[j].taskList[i].curTime.minutes
                re = self.bot.userSchedules[j].remind
                if (da*1440+hr*60+mi-re == curTime.tm_mday*1440+curTime.tm_hour*60+curTime.tm_min):
                    chan = self.bot.userSchedules[j].channel
                    if (chan == None): continue
                    user = await self.bot.fetch_user(int(j))
                    await chan.send(f"{user.mention} Task Reminder!!")
                    await chan.send("Your task %s is upcoming in %s" % (self.bot.userSchedules[j].taskList[i].name, self.convert(self.bot.userSchedules[j].remind)))

    @tasks.loop(minutes=1.0)
    async def autodelete(self):
        curTime = time.localtime()
        for j in self.bot.userSchedules:
            while len(self.bot.userSchedules[j].taskList) > 0: 
                da = self.bot.userSchedules[j].taskList[0].curTime.day
                hr = self.bot.userSchedules[j].taskList[0].curTime.hours
                mi = self.bot.userSchedules[j].taskList[0].curTime.minutes
                if (da*1440+hr*60+mi <= curTime.tm_mday*1440+curTime.tm_hour*60+curTime.tm_min):
                    chan = self.bot.userSchedules[j].channel
                    # if (chan != None):
                    #     user = await self.bot.fetch_user(int(j))
                    #     await chan.send(f"{user.mention} Task Reminder!!")
                    #     await chan.send("Your task %s is upcoming in %s" % (self.bot.userSchedules[j].taskList[i].name, self.convert(self.bot.userSchedules[j].remind)))
                    self.bot.userSchedules[j].taskList.pop(0)
                else: break


def setup(bot): 
    bot.add_cog(Ready(bot))