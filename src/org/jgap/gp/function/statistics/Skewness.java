/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.gp.function.statistics;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.impl.*;
import org.jgap.util.*;

/**
 * Computes the skewness of the available values.
 * <p>
 * We use the following (unbiased) formula to define skewness:</p>
 * <p>
 * skewness = [n / (n -1) (n - 2)] sum[(x_i - mean)^3] / std^3 </p>
 * <p>
 * where n is the number of values, and std is the Standard Deviation </p>
 *
 * @author Luis Garcia
 * @since 3.5
 */
public class Skewness
    extends CommandDynamicArity implements ICloneable {
  public Skewness(final GPConfiguration a_conf, Class a_returnType,
                  int a_arityInitial, int a_arityMin, int a_arityMax)
      throws InvalidConfigurationException {
    super(a_conf, a_arityInitial + 1, a_arityMin + 1, a_arityMax + 1,
          a_returnType);
  }

  @Override
  public double execute_double(ProgramChromosome c, int n, Object[] args) {
    int size = size();
    DescriptiveStatistics stats = new DescriptiveStatistics();
    for (int i = 0; i < size; i++) {
      stats.addValue(c.execute_double(n, i, args));
    }
    return stats.getSkewness();
  }

  @Override
  public float execute_float(ProgramChromosome c, int n, Object[] args) {
    int size = size();
    DescriptiveStatistics stats = new DescriptiveStatistics();
    for (int i = 0; i < size; i++) {
      stats.addValue(c.execute_float(n, i, args));
    }
    return (float) stats.getSkewness();
  }

  @Override
  public String toString() {
    String s = "Skewness(";
    int size = size();
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        s += ";";
      }
      s += "&" + (i + 1);
    }
    return s + ")";
  }

  @Override
  public Object clone() {
    try {
      Skewness result = new Skewness(getGPConfiguration(), getReturnType(),
                                     getArity(null), getArityMin(), getArityMax());
      return result;
    } catch (Exception ex) {
      throw new CloneException(ex);
    }
  }

  @Override
  public String getName() {
    return "Skewness";
  }
}
