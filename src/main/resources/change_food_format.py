import csv
import json
import os

def csv_to_json_by_category(csv_file, output_folder):
    # Créer un dictionnaire pour stocker les données par catégorie
    data = []

    with open(csv_file, "r") as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            food_name = row["food_name"]
            calories_per_100_ml_or_gms = int(row["cal_per_100_ml_or_gms"].replace(" cal", "")) if row["cal_per_100_ml_or_gms"] else None
            cal_per_serving = int(row["cal_per_serving"].replace(" cal", "")) if row["cal_per_serving"] else None
            serving_quantity = row["per_serving"] if row["per_serving"] else None

            data.append({
                "name": food_name,
                "caloriesPer100": calories_per_100_ml_or_gms,
                "caloriesPerServing": cal_per_serving,
                "servingQuantity": serving_quantity
            })

    data.sort(key=lambda x: x["name"])

    with open(output_file, "w") as jsonfile:
        json.dump(data, jsonfile, indent=4)


csv_file = "src/main/resources/calorie_infos.csv"  # Chemin vers votre fichier CSV
output_file = "src/main/resources/food.json"  # Dossier de sortie pour les fichiers JSON

csv_to_json_by_category(csv_file, output_file)
