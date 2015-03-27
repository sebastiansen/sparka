package features.extractors

import features.Feature

object FeatureValue{
  implicit class FeatureValue(feature: Feature){
    def value(input: Input): Double = 0.0
  }
}
