package features.extractors

import breeze.numerics.log
import features._

trait Extractable

trait DeviceType

object TABLET extends DeviceType

object MOBILE extends DeviceType

object GAME_CONSOLE extends DeviceType

object UNKNOWN extends DeviceType

object DMR extends DeviceType

object COMPUTER extends DeviceType

case class Input(rooms: Integer, deviceType: DeviceType) extends Extractable

abstract case class FeaturesExtractor[E <: Extractable](extractable: E) {
  var featuresMap: Map[Feature, Double] = Map()

  def features(features: Seq[Feature]): Unit = {
    this.featuresMap = features.map({ feature => (feature, 0.0) }).toMap
  }

  def logValue(value: Double): Double =
    if (value > -1) log(1.0 + value) else -1.0

  def set(feature: Feature, value: Double): Unit = featuresMap + (feature -> value)

  def set(feature: Feature, value: Boolean): Unit = featuresMap + (feature -> value)

  def set(feature: Feature): Unit = this.set(feature, 1.0)

  def setWithLog(feature: Feature, logFeature: Feature, value: Double): Unit = {
    this.set(feature, value)
    this.set(logFeature, logValue(value))
  }
}

class RoomsFeaturesExtractor(input: Input) extends FeaturesExtractor(input: Input) {
  features(Seq(ROOMS, ROOMS_LOG))

  if (input.rooms != null) setWithLog(ROOMS, ROOMS_LOG, input.rooms.toDouble)
}

class DeviceTypeFeaturesExtractor(input: Input) extends FeaturesExtractor(input: Input) {
  features(Seq(
    DEVICE_TYPE_DESKTOP,
    DEVICE_TYPE_TABLET,
    DEVICE_TYPE_PHONE,
    DEVICE_TYPE_OTHER
  ))

  set(input.deviceType match {
    case null => DEVICE_TYPE_DESKTOP
    case MOBILE => DEVICE_TYPE_PHONE
    case TABLET => DEVICE_TYPE_TABLET
    case DMR | GAME_CONSOLE => DEVICE_TYPE_OTHER
    case COMPUTER | UNKNOWN => DEVICE_TYPE_DESKTOP
  })
}

class InputFeaturesExtractor(input: Input) extends FeaturesExtractor[Input](input: Input) {
  featuresMap = Seq(
    new RoomsFeaturesExtractor(input),
    new DeviceTypeFeaturesExtractor(input)
  ).map(_.featuresMap).reduce(_ ++ _)
}