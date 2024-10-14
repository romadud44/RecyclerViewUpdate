package com.example.recyclerview

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable


@SuppressLint("ParcelCreator")
data class Thing(val id: Int, val name: String?, val info: String?, val image: String?) :
    Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(info)
        dest.writeString(image)
    }

    override fun toString(): String {
        return "$name индекс: $id"
    }

    companion object CREATOR : Parcelable.Creator<Thing> {
        override fun createFromParcel(parcel: Parcel): Thing {
            return Thing(parcel)
        }

        override fun newArray(size: Int): Array<Thing?> {
            return arrayOfNulls(size)
        }

        val things = mutableListOf(
            Thing(0, "Пальто", "Пальто черное", R.drawable.image_thing_1.toString()),
            Thing(1, "Футболка", "Футболка Кострома", R.drawable.image_thing_2.toString()),
            Thing(2, "Шарф", "Шарф белый", R.drawable.image_thing_3.toString()),
            Thing(3, "Джинсы", "Джинсы голубые", R.drawable.image_thing_4.toString()),
            Thing(4, "Шапка", "Шапка обычная, черная", R.drawable.image_thing_5.toString()),
            Thing(5, "Шапка", "Шапка - ушанка, черная", R.drawable.image_thing_6.toString()),
            Thing(6, "Платок", "Платок, зеленый", R.drawable.image_thing_7.toString()),
            Thing(7, "Кот?", "Опять спрятался здесь", R.drawable.image_thing_8.toString()),
            Thing(8, "Носки", "Носки цвета хаки", R.drawable.image_thing_9.toString()),
            Thing(9, "Варежки", "Варежки бежевые", R.drawable.image_thing_10.toString()),
            Thing(10, "Перчатки", "Перчатки черные", R.drawable.image_thing_11.toString()),
            Thing(
                11,
                "Спортивный костюм",
                "Костюм китайский, синий",
                R.drawable.image_thing_12.toString()
            ),
            Thing(12, "Валенки", "Валенки серые", R.drawable.image_thing_13.toString()),
            Thing(13, "Сапоги", "Сапоги резиновые, черные", R.drawable.image_thing_14.toString()),
            Thing(14, "Тапки", "Тапки мягкие, синие", R.drawable.image_thing_15.toString()),
            Thing(15, "Сумка", "Сумка дорожная", R.drawable.image_thing_16.toString()),
            Thing(16, "Лыжи", "Лыжи синие", R.drawable.image_thing_17.toString()),
            Thing(17, "КПБ", "Комплект постельного белья", R.drawable.image_thing_18.toString()),
            Thing(18, "Шорты", "Шорты синие", R.drawable.image_thing_19.toString()),
            Thing(19, "Кофта", "Кофта оверсайз, черная", R.drawable.image_thing_20.toString()),
        )
        var thingsDb: MutableList<Thing> = mutableListOf()


    }
}
