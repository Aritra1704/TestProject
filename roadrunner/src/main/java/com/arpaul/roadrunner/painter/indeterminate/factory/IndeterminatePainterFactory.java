package com.arpaul.roadrunner.painter.indeterminate.factory;

import android.view.View;

import com.arpaul.roadrunner.painter.configuration.PathPainterConfiguration;
import com.arpaul.roadrunner.painter.configuration.indeterminate.MaterialPainterConfiguration;
import com.arpaul.roadrunner.painter.configuration.indeterminate.TwoWayIndeterminateConfiguration;
import com.arpaul.roadrunner.painter.indeterminate.IndeterminatePainter;
import com.arpaul.roadrunner.painter.indeterminate.IndeterminatePathPainter;
import com.arpaul.roadrunner.painter.indeterminate.MaterialPainter;
import com.arpaul.roadrunner.painter.indeterminate.TwoWayIndeterminatePainter;
import com.arpaul.roadrunner.path.PathContainer;

/**
 * @author Adrián García Lomas
 */
public class IndeterminatePainterFactory {

  public static IndeterminatePathPainter makeIndeterminatePathPainter(IndeterminatePainter indeterminatePainter,
                                                                      PathContainer pathContainer, View view, PathPainterConfiguration pathPainterConfiguration) {

    switch (indeterminatePainter) {
      case TWO_WAY:
        return makeTwoWayPainter(pathContainer, view, pathPainterConfiguration);
      case MATERIAL:
        return makeMaterialPainter(pathContainer, view, pathPainterConfiguration);
      default:
        return makeTwoWayPainter(pathContainer, view, pathPainterConfiguration);
    }
  }

  private static IndeterminatePathPainter makeMaterialPainter(PathContainer pathContainer,
      View view, PathPainterConfiguration pathPainterConfiguration) {
    return new MaterialPainter(pathContainer, view,
        (MaterialPainterConfiguration) pathPainterConfiguration);
  }

  private static TwoWayIndeterminatePainter makeTwoWayPainter(PathContainer pathContainer,
                                                              View view,
                                                              PathPainterConfiguration pathPainterConfiguration) {
    return new TwoWayIndeterminatePainter(view, pathContainer,
        (TwoWayIndeterminateConfiguration) pathPainterConfiguration);
  }
}
