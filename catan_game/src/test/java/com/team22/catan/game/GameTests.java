package com.team22.catan.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  DiceTest.class,
  GameTest.class,
  ParserTest.class
})
public class GameTests {}
