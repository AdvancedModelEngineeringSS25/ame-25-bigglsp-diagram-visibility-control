# Overview

Below is an outline of the packages employed within our system.

## Core

The `core` package modifies GLSP to fit our specific needs. It changes certain parts to work better with our system, making everything run smoothly and according to our own rules and design. The most important sub-packages are as follows. In general, the core package is responsible for bootstraping the whole application.

### Manifest & Contribution

The [manifest](./manifest.md) package provides the base classes to make the manifest & [contribution](./contribution.md) system work. More about it later.

### Model

Model specific sub-packages are the following:

- `model`: In GLSP, a `model` is like a blueprint for a diagram. It includes all the parts of the diagram, like shapes and lines, and how they're connected. It's the basic setup that GLSP uses to create and manage the diagram. The `model` package is responsible for all tasks thar are necessary to load, save and process the underlying source models (e.g., the file where the diagram is saved).
- `gmodel`: A `gmodel` in GLSP represents the graphical elements and layout of a diagram. The source model elements are mapped to a `GModel`.
- `commands`: In GLSP, `commands` are instructions that specific actions, like adding or removing elements, changing their properties, or rearranging them in a diagram.

### Features

The core-specific features are features that come from GLSP and that we customize or are general purpose features.

### Action & Operation

The terminology difference is explained in the [GLSP Actions & Action Handlers](https://eclipse.dev/glsp/documentation/actionhandler/) and [GLSP Model Operations](https://eclipse.dev/glsp/documentation/modeloperations/) documentation.

In principle, we delegate most operations to a **element-specific handler**. For example, the `DeleteOperation` has it's own `DeleteOperationHandler` and within, we delegate the `DeleteOperation` to a registered `element-handler`. Consequently, the `DeleteOperation` will be handled directly by the element (e.g., the targeted node, edge).

## Elements

Next to our `core` package, we have the `element` package. This package focuses on exposing base classes to allow better handling of specific elements used in our diagrams, like shapes, lines, and icons. The individual diagram element implementations will usually extend the classes exposed here.

## Features

Following the `core` and `element` packages, we have a `feature` package. This package adds new complex functionalities that aren't found in the standard GLSP. It's designed to enhance our diagramming tools with unique capabilities, tailored to our specific requirements and workflow such as the `outline`, `autocomplete` and `property palette`.

## SDK

Lastly, we have the `sdk` package. This package provides tools and components that make it easier to create and manage gmodels, or graphical models, for our diagrams. It's like a toolkit that simplifies the process of building and customizing the visual elements in our system.

It uses the concepts shown in the [GLSP Graphical Model documentation](https://eclipse.dev/glsp/documentation/gmodel/). It is not necessary to use the toolkit, it is also possible to follow the standard approach.
