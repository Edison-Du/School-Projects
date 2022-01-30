import discord
from discord.ext import commands
import os
import json

bot = commands.Bot(command_prefix = "!")
dirPath = os.path.dirname(os.path.realpath(__file__))

async def is_owner(ctx):
    return (ctx.author.id == "Your ID here") # Owner id


bot.remove_command("help")


bot.load_extension(f"cogs.ready")
bot.load_extension(f"cogs.good")

@bot.event
async def on_command_error(ctx, error):
    if isinstance(error, (commands.MissingRequiredArgument)):
        await ctx.send(f"{ctx.author.mention} Please input all necessary parameters")
    elif isinstance(error, (commands.CommandNotFound)):
        await ctx.send(f"{ctx.author.mention} Command '{ctx.message.content}' not found. Please type a valid command.")
    else: raise error

bot.run('TOKEN HERE') # Discord bot token
