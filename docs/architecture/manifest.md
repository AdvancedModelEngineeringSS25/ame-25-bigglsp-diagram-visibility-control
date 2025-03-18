# Manifest

Manifests play a crucial role in our system: they list all the settings and customizations (bindings) a user wants to add to our dependency injection system. Each feature and (diagram) element in our system has its own manifest, where you can see all the exposed definitions. This setup helps organize and apply the specific configurations and functionalities required for each part of our application, ensuring everything operates seamlessly together.

## Core

The core package exposes the following manifests:

- `BGContributionManifest`: All contribution classes extend from this classes. See [Contribution documentation](./contribution.md) for more.
- `BGFeatureManifest`: All features (in all levels) extend from this class.
- `BGRepresentationManifest`: Diagrams extend this class. The base class requires a representation. Some components use the representation provided in the class to decide which bindings from which dependency injection container to load.

## Element

The element package exposes the following manifests:

- `BGElementManifest`: The base class for all element based manifests.
- `BGNodeElementManifest`: Node-specific diagram element implementations need to extend this class.
- `BGEdgeElementManifest`: Edge-specific diagram element implementations need to extend this class.

There are also integrations for `EMF`.
