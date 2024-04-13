import csv
import json


def csv_to_json(csv_file, output_file):
    with open(csv_file, "r") as csvfile:
        data = sorted(
            [
                {
                    "name": row["food_name"],
                    "caloriesPer100": int(
                        row["cal_per_100_ml_or_gms"].replace(" cal", "")
                    )
                    if row["cal_per_100_ml_or_gms"]
                    else None,
                    "caloriesPerServing": int(
                        row["cal_per_serving"].replace(" cal", "")
                    )
                    if row["cal_per_serving"]
                    else None,
                    "servingQuantity": row["per_serving"]
                    if row["per_serving"]
                    else None,
                }
                for row in csv.DictReader(csvfile)
            ],
            key=lambda x: x["name"],
        )

    with open(output_file, "w") as jsonfile:
        json.dump(data, jsonfile, indent=4)


csv_to_json(
    "src/main/resources/ulb/calorie_infos_fr.csv",
    "src/main/resources/ulb/jsons/food.json",
)
