import csv
import json
import os

def csv_to_json_by_category(csv_file, output_folder):
    # Créer un dictionnaire pour stocker les données par catégorie
    data_by_category = {}

    with open(csv_file, "r") as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            category = row["food_category"]
            if category not in data_by_category:
                data_by_category[category] = []

            food_name = row["food_name"]
            calories_per_100_ml_or_gms = int(row["cal_per_100_ml_or_gms"].replace(" cal", "")) if row["cal_per_100_ml_or_gms"] else None
            serving_quantity = row["per_serving"] if row["per_serving"] else None

            data_by_category[category].append({
                "name": food_name,
                "caloriesPer100": calories_per_100_ml_or_gms,
                "servingQuantity": serving_quantity
            })

    # Assurer l'existence du dossier de sortie
    os.makedirs(output_folder, exist_ok=True)

    # Écrire les données dans des fichiers JSON par catégorie
    for category, data in data_by_category.items():
        json_file = os.path.join(output_folder, f"{category}.json")
        with open(json_file, "w") as jsonfile:
            json.dump(data, jsonfile, indent=4)

csv_file = "src/main/resources/calorie_infos.csv"  # Chemin vers votre fichier CSV
output_folder = "src/main/resources/food"  # Dossier de sortie pour les fichiers JSON

csv_to_json_by_category(csv_file, output_folder)
