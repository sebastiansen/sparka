import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.{DoubleParam, Params, ParamMap}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType

class FeaturesExtractor extends Transformer{
  override def transform(dataset: DataFrame, paramMap: ParamMap): DataFrame = {
    dataset
  }

  @DeveloperApi
  override def transformSchema(schema: StructType, paramMap: ParamMap): StructType = ???
}