# A* star

A simple project to demonstrate the use of some basic (tree) search algorithms.

## Structure

There are three primary directories in `src`:

* `a_star_search` has the `core` namespace that includes the `-main` function that you can use to run everything.
* `problems` has separate namespaces for each problem that's been implemented, e.g., `n-puzzle` and `random-walk`.
* `search/algorithms` has the functions that implement the different seach algorithms (e.g., `breadth-first-search` and `shortest-path`).

## Usage

You can run the search algorithms using the `a-star-search.core/-main` function. If you want to do this from the command line, you can use `lein`:

    $ lein run

If you want to run it from inside something like LightTable, you can select the `-main` function and press `CTRL-Return` to cause that expression to be evaluated.

## Examples

Below is an example of a `-main` function that performs a shortest path search of an 8-puzzle instance, using a cost function that prefers horizontal moves (or, equivalently, penalizes vertical moves):

```clojure
(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np])
  (:gen-class))

(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state (np/->State [[0 1 3] [4 2 5] [7 8 6]] [0 0])
        goal-state (np/->State [[0 1 2] [3 4 5] [6 7 8]] [0 0])
        max-states 1000000
        [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
                                          max-states start-state goal-state)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)
    ))
```

We start by constructing the starting and goal states for the puzzle, and setting the maximum number of states we want to explore to 1,000,000 which is (I think) enough to search all possible states for a simple 8-puzzle problem. We then call `alg/shortest-path` with the `children` and `prefer-horizontal-cost` functions from the `problems.n-puzzle` namespace, along with `max-states` and the start and goal states. This returns a vector of two values, which we deconstruct right in the `let`, assigning them to `result` and `costs`. Finally we extract the `path` from the result by essentially walking backwards through the `came-from` map from the goal state to the start state. We finally call `print-results` to print out the results of the search.

## License

Copyright © 2016 Nic McPhee

Distributed under the MIT License.
