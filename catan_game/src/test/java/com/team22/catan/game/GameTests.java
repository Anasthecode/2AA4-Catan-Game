package com.team22.catan.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  ComputerPlayerTest.class,
  DiceTest.class,
  GameTest.class
})
public class GameTests {}
