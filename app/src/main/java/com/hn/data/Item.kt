package com.hn.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by stevenpungdumri on 5/8/17.
 */

data class Item(
    val id: Long,
    val deleted: Boolean,
    val type: HNItemType,
    val by: String,
    val time: Long,
    val text: String?,
    val dead: Boolean,
    val parent: Long,
    val poll: Long,
    val kids: List<Long>,
    val url: String?,
    val score: Int,
    val title: String?,
    val parts: LongArray?,
    val descendants: Int,
    var comments: List<Item>,
    var indentation: Int
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Item> = object : Parcelable.Creator<Item> {
            override fun createFromParcel(source: Parcel): Item = Item(source)
            override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readLong(),
    1 == source.readInt(),
    HNItemType.values()[source.readInt()],
    source.readString(),
    source.readLong(),
    source.readString(),
    1 == source.readInt(),
    source.readLong(),
    source.readLong(),
    ArrayList<Long>().apply { source.readList(this, Long::class.java.classLoader) },
    source.readString(),
    source.readInt(),
    source.readString(),
    source.createLongArray(),
    source.readInt(),
    ArrayList<Item>().apply { source.readList(this, Item::class.java.classLoader) },
    source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeInt((if (deleted) 1 else 0))
        dest.writeInt(type.ordinal)
        dest.writeString(by)
        dest.writeLong(time)
        dest.writeString(text)
        dest.writeInt((if (dead) 1 else 0))
        dest.writeLong(parent)
        dest.writeLong(poll)
        dest.writeList(kids)
        dest.writeString(url)
        dest.writeInt(score)
        dest.writeString(title)
        dest.writeLongArray(parts)
        dest.writeInt(descendants)
        dest.writeList(comments)
        dest.writeInt(indentation)
    }
}
