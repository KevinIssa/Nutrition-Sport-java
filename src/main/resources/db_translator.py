import requests
import csv
import deepl
import os

translator = deepl.Translator(auth_key="e040a1fa-d658-486a-bb19-fa17bfeb6a4e:fx")

def translate_text(text, target_lang):
    result = translator.translate_text(text, target_lang=target_lang)
    return result

def translate_csv(input_file, output_file, field_to_translate, target_lang):
    with open(input_file, 'r', newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        translated_rows = []
        count = 0
        for row in reader:
            if field_to_translate in row:
                row[field_to_translate] = translate_text(row[field_to_translate], target_lang)
            translated_rows.append(row)
            count += 1
            if count % 10 == 0:
                print(f"Translated {count} rows.")


    fieldnames = translated_rows[0].keys()

    with open(output_file, 'w', newline='', encoding='utf-8') as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(translated_rows)

# Example usage
input_file = 'calorie_infos.csv'
output_file = 'calorie_infos_fr.csv'
field_to_translate = 'food_name'
target_language = 'FR'
translate_csv(input_file, output_file, field_to_translate, target_language)

print("Translation completed and saved to 'translated_output.csv'.")