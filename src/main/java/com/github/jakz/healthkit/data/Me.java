package com.github.jakz.healthkit.data;

import java.time.LocalDate;

import com.github.jakz.healthkit.data.constants.BloodType;
import com.github.jakz.healthkit.data.constants.Sex;
import com.github.jakz.healthkit.data.constants.SkinType;

public class Me
{
  private LocalDate birth;
  private Sex sex;
  private BloodType blood;
  private SkinType skin;
  
  public Me()
  {
    
  }
  
  public Sex sex() { return sex; }
  public void sex(Sex sex) { this.sex = sex; }
  
  public LocalDate birth() { return birth; }
  public void birth(LocalDate birth) { this.birth = birth; }
  
  public BloodType blood() { return blood; }
  public void blood(BloodType blood) { this.blood = blood; }
  
  public SkinType skin() { return skin; }
  public void skin(SkinType skin) { this.skin = skin; }
}
