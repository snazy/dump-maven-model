/*
 * Copyright (C) 2022 Robert Stupp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.caffinitas.maven.dumpmodel;

import org.apache.maven.execution.MavenSession;

public class ModelContainer {

  private final ModelWrapper model;
  private final ProjectsHierarchy hierarchy;

  public ModelContainer(ProjectsHierarchy hierarchy) {
    this.hierarchy = hierarchy;
    this.model = new ModelWrapper(hierarchy, hierarchy.getRoot());
  }

  public static ModelContainer create(MavenSession session) {
    ProjectsHierarchy hierarchy = ProjectsHierarchy.createHierarchyFromMavenSession(session);

    return new ModelContainer(hierarchy);
  }

  public ModelWrapper getModel() {
    return model;
  }
}
