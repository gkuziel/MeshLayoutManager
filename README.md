# Digiteq-Assignment

A custom LayoutManager and SnapHelper with PagerView feeling, supporting scroll, drag & drop, reversed layout and item animations.
Items in each page are drawn top to the bottom - raw by raw.

The project consists of 2 modules:
- assignment library 
- demo/test app

# Requirements
android:minSdkVersion = 24

# Usage

Just as standard LayoutManager and SnapHelper:
```
  val yourCustomAdapter = YourCustomAdapter()

  // define page dimensions and reverse setting
  val columnCount = 5
  val rowCount = 2
  val reversed = false

  val meshSnapHelper = MeshSnapHelper(
	  columnCount,
	  rowCount,
	  reversed
  )

  with(binding) {
  	recyclerview.layoutManager = MeshLayoutManager(
  		this@MainActivity,
  		columnCount,
  		rowCount,
  		reversed
  	)
  	recyclerview.adapter = YourCustomAdapter()
  	meshSnapHelper.attachToRecyclerView(recyclerview)
  }

  yourCustomAdapter.setItems(/* dataset */)

```

# Demo

Demo app main screen contains toggles allowing:
- switching between LayoutManagers (MeshLayoutManager vs standard LayoutManagers)
- setting MeshLayoutManager page dimensions
- switching between SnapHelpers (MeshSnapHelper vs standard SnapHelper)
- enabling: RTL, LTR
- scrolling across the RecyclerView
- triggering item animations
- 2 RecyclerViews (the second one for testing Drag&Drop feature)

info: Each RecyclerView item has 2 TextViews:
- orange displays value of data
- green displays position in the dataset


![alt tag](https://github.com/gkuziel/Digiteq-Assignment/assets/5773920/03ac4001-18e6-4a0b-b41d-9386857a3261)


# TODO:

1. Test Accessibility support.
6. Addition testing.
7. Optimize binding algorithm while scrolling.
