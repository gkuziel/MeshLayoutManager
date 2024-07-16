# Assignment requirements

Custom LayoutManager and SnapHelper:

- Populates items in a grid horizontally, like:

---
    item_1  item_2  item_3  item_4  item_5  |  item_11  item_12  item_13  item_14  item_15
    item_6  item_7  item_8  item_9  item_10 |  item_16  item_17  ...
---
- number of rows and columns are configurable
- right to left support
- supports RecyclerView item animations
- supports smooth scroll
- supports drag & drop (2 RecyclerViews)
- displays and scroll always the full page, where the page contains `[number of columns] x [number of rows]` items
- n-th page start with `[number of columns] x [number of rows] * n + 1` item

# Solution

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


![alt tag](https://github.com/user-attachments/assets/902efbe4-3f46-4290-add1-d0b2a1999bda)


# TODO:

1. Improve item height handling 
2. Test Accessibility support
3. Additional testing
4. Optimize binding algorithm while scrolling

# License

```
   Copyright 2024 gkuziel
	
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
	
      http://www.apache.org/licenses/LICENSE-2.0
	
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
